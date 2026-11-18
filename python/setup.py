from setuptools import setup

with open("README.md", "r") as fh:
    long_description = fh.read()

setup(
    name="uvlparser",
    version="2.5.0",
    description="This module provides a get_tree function to obtain an ANTLR parse-tree from a UVL-defined feature model",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/Universal-Variability-Language/uvl-parser",
    author="UVL Team",
    author_email="jagalindo@us.es",
    # To find more licenses or classifiers go to: https://pypi.org/classifiers/
    license="GNU General Public License v3 (GPLv3)",
    # 'main' stays a top-level module for existing `from main import get_tree` users;
    # the tests and the stray top-level __init__.py are no longer installed.
    packages=['uvl'],
    py_modules=['main'],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: GNU General Public License v3 (GPLv3)",
        "Operating System :: OS Independent",
    ],
    zip_safe=True,
    python_requires=">=3.0",
    install_requires=[
        "antlr4-python3-runtime==4.13.2",
    ],
)
