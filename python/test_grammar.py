import pytest
import glob
import os

from main import get_tree

TEST_MODELS_DIR = os.path.join(os.path.dirname(__file__), "..", "test_models")

# Files that are misplaced in test_models/ but actually test invalid syntax
# (dotinfeaturename.uvl has comment "should be illegal" - dots not allowed in quoted IDs)
MISPLACED_INVALID_FILES = {
    "lexing/dotinfeaturename.uvl",
}

# Semantic errors that the parser cannot detect (only syntax errors are caught)
SEMANTIC_ERROR_FILES = {
    "missingreference.uvl",
    "wrong_attribute_name.uvl",
    "same_feature_names.uvl",
}


def relative_path(file):
    return os.path.relpath(file, TEST_MODELS_DIR)


def get_valid_files():
    all_files = glob.glob(os.path.join(TEST_MODELS_DIR, "**", "*.uvl"), recursive=True)
    valid = [f for f in all_files if os.sep + "faulty" + os.sep not in f]
    return [f for f in valid if relative_path(f) not in MISPLACED_INVALID_FILES]


def get_faulty_syntax_files():
    """Only return files with syntax errors (not semantic errors)."""
    all_faulty = glob.glob(os.path.join(TEST_MODELS_DIR, "faulty", "*.uvl"))
    return [f for f in all_faulty if os.path.basename(f) not in SEMANTIC_ERROR_FILES]


@pytest.mark.parametrize("uvl_file", get_valid_files(), ids=relative_path)
def test_valid_model(uvl_file):
    tree = get_tree(uvl_file)
    assert tree is not None


@pytest.mark.parametrize("uvl_file", get_faulty_syntax_files(), ids=relative_path)
def test_faulty_model(uvl_file):
    with pytest.raises(Exception):
        get_tree(uvl_file)
