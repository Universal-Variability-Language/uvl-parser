from setuptools import setup

with open("README.md", "r") as fh:
    long_description = fh.read()

setup(
    name="uvlparser",
    version="2.0.0",
    description="This module provides a get_tree function to obtain an ANTLR parse-tree from a UVL-defined feature model",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/Universal-Variability-Language/uvl-parser",
    author="UVL Team",
    author_email="jagalindo@us.es",
    # To find more licenses or classifiers go to: https://pypi.org/classifiers/
    license="GNU General Public License v3 (GPLv3)",
    packages=['.','uvl'],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: GNU General Public License v3 (GPLv3)",
        "Operating System :: OS Independent",
    ],
    zip_safe=True,
    python_requires=">=3.0",
    install_requires=[
        "antlr4-python3-runtime==4.13.1",
    ],
)
