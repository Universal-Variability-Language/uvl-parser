import pytest
import glob
import os

from main import get_tree

TEST_MODELS_DIR = os.path.join(os.path.dirname(__file__), "..", "test_models")


def get_valid_files():
    all_files = glob.glob(os.path.join(TEST_MODELS_DIR, "**", "*.uvl"), recursive=True)
    return [f for f in all_files if os.sep + "faulty" + os.sep not in f]


def get_faulty_files():
    return glob.glob(os.path.join(TEST_MODELS_DIR, "faulty", "*.uvl"))


def relative_path(file):
    return os.path.relpath(file, TEST_MODELS_DIR)


@pytest.mark.parametrize("uvl_file", get_valid_files(), ids=relative_path)
def test_valid_model(uvl_file):
    tree = get_tree(uvl_file)
    assert tree is not None


@pytest.mark.parametrize("uvl_file", get_faulty_files(), ids=relative_path)
def test_faulty_model(uvl_file):
    with pytest.raises(Exception):
        get_tree(uvl_file)
