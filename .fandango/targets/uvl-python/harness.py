"""Parse one file with the Python parser of this checkout (`make python_parser` done): exit 0 if it parses."""

import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[3] / "python"))
sys.setrecursionlimit(10_000)

from main import get_tree  # noqa: E402

try:
    get_tree(sys.argv[1])
except RecursionError:
    print("RecursionError: nesting too deep", file=sys.stderr)
    sys.exit(1)
except Exception as exc:  # whatever the parser raises is a rejection
    print(f"{type(exc).__name__}: {' '.join(str(exc).split())}", file=sys.stderr)
    sys.exit(1)
