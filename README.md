[![CI Build with tests](https://github.com/jdbi/jdbi3-oracle12/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/jdbi/jdbi3-oracle12/actions/workflows/ci.yml)

# Oracle support for JDBI 3

This package is set apart from the [main JDBI](https://github.com/jdbi/jdbi) build for historical reasons.


## Releasing

This package is intended to be released whenever a [main JDBI](https://github.com/jdbi/jdbi) release is done.

- Submit a PR that changes the parent version and the `dep.jdbi3.version` property to the just released main version
- Once the PR passes all tests, merge it
- Run the release steps as described [in the JDBI release document](https://github.com/jdbi/jdbi/blob/master/RELEASE_STEPS.md).
