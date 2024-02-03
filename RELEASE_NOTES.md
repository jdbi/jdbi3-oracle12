# Release Notes

This file will only list significant releases that have actual
changes. If a release is not listed here, it contains the same code as
the previous release except it was compiled against the matching
release of the main Jdbi code base.

Current release is 3.44.0

# 3.43.0

* Moved JUnit 4 OracleDatabaseRule to main jar.

# 3.42.0

* synced with mainline 3.42.0 release
* Add slow markers for tests

# 3.39.1 - ** Last release to support Java 8!**

* synced with mainline 3.39.1 release
* deployed site for javadoc
* fix longstanding documentation error

# 3.38.3

* build system backports from 3.38.3 release
* new parent pom (org.jdbi:jdbi3-build-parent)
* tweak test selection on amd64 or x86_64

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
