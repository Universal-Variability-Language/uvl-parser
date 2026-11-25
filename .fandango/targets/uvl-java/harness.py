"""Parse one file with the Java parser of this checkout (`cd java && mvn package` done): exit 0 if it parses.

The jar does not bring the ANTLR runtime, which is taken from Maven's local repository. Harness.java is
compiled once, next to the jar. The JVM is told to reserve little memory: the fuzzer caps the address
space of what it runs (2 GB on Linux), and a JVM with its defaults asks for more than that before it starts.
"""

import glob
import os
import subprocess
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
TARGET = HERE.parents[2] / "java" / "target"
ANTLR = Path.home() / ".m2/repository/org/antlr/antlr4-runtime/4.13.2/antlr4-runtime-4.13.2.jar"


SMALL = ["-Xmx256m", "-XX:+UseSerialGC", "-XX:ReservedCodeCacheSize=32m", "-XX:CompressedClassSpaceSize=32m",
         "-XX:MaxMetaspaceSize=96m"]


def fail(message: str) -> None:
    print(message, file=sys.stderr)
    sys.exit(127)


jars = sorted(p for p in glob.glob(str(TARGET / "uvl-parser-*.jar")) if not p.endswith(("-sources.jar", "-javadoc.jar")))
if not jars:
    fail(f"no uvl-parser jar in {TARGET}: build it with `mvn package`")
if not ANTLR.exists():
    fail(f"no ANTLR runtime at {ANTLR}")

classpath = os.pathsep.join([jars[-1], str(ANTLR)])
cache = TARGET / "fuzz-harness"
if not (cache / "Harness.class").exists():
    cache.mkdir(parents=True, exist_ok=True)
    done = subprocess.run(["javac", *("-J" + flag for flag in SMALL), "-cp", classpath, "-d", str(cache), str(HERE / "Harness.java")],
                          capture_output=True, text=True)
    if done.returncode != 0:
        fail("cannot compile the harness: " + " | ".join((done.stderr + done.stdout).split("\n")))

done = subprocess.run(["java", *SMALL, "-Xss8m", "-cp", os.pathsep.join([str(cache), classpath]), "Harness", sys.argv[1]],
                      capture_output=True, text=True, stdin=subprocess.DEVNULL)
sys.stderr.write(done.stderr or done.stdout)  # the JVM says on stdout why it could not start
sys.exit(done.returncode)
