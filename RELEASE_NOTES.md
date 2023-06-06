# Unreleased

# 3.38.3
* build system backports from 3.38.3 release
* new parent pom (org.jdbi:jdbi3-build-parent)
* tweak test selection on amd64 or x86_64

# 3.38.0
* build system updates

# 3.37.0
* only run tests on x86_64

# 3.36.0

* Support `ResultProducers#allowNoResults` flag for OracleReturning#returnParameters()
* Remove unofficial `JdbiOracle12Extension`, use test container support for Jdbi
* add unit test/example for Outparameter Cursor (based on https://github.com/jdbi/jdbi/issues/2231)

# 3.35.0

* Align behavior of OracleReturning#returnParameters() with ResultProducers#returningResults() when
    `allowNoResults` is false.
* Use JUnit 5 for tests
