#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

#
# Makefile for jdbi build targets
#
SHELL = /bin/sh
.SUFFIXES:
.PHONY: help clean install install-nodocker install-fast tests tests-nodocker deploy release

MAVEN = ./mvnw ${JDBI_MAVEN_OPTS}

export MAVEN_OPTS MAVEN_CONFIG

default: help

clean:
	${MAVEN} clean

install:
	${MAVEN} clean install

install-nodocker: MAVEN_CONFIG += -Dno-docker=true
install-nodocker: install

install-fast: MAVEN_CONFIG += -Pfast
install-fast: install

tests: MAVEN_CONFIG += -Dbasepom.it.skip=false
tests:
	${MAVEN} surefire:test invoker:install invoker:integration-test invoker:verify

tests-nodocker: MAVEN_CONFIG += -Dno-docker=true
tests-nodocker: tests

deploy: MAVEN_CONFIG += -Dbasepom.it.skip=false
deploy:
	${MAVEN} clean deploy

# run install b/c https://issues.apache.org/jira/browse/MJAVADOC-701
deploy-site:
	${MAVEN} clean install site-deploy

release:
	${MAVEN} clean release:clean release:prepare release:perform

help:
	@echo " * clean            - clean local build tree"
	@echo " * install          - builds and installs the current version in the local repository"
	@echo " * install-nodocker - same as 'install', but skip all tests that require a local docker installation"
	@echo " * install-fast     - same as 'install', but skip test execution and code analysis (Checkstyle/PMD/Spotbugs)"
	@echo " * tests            - run all unit and integration tests"
	@echo " * tests-nodocker   - same as 'tests', but skip all tests that require a local docker installation"
	@echo " *"
	@echo " ***********************************************************************"
	@echo " *"
	@echo " * privileged targets (require project privileges)"
	@echo " *"
	@echo " ***********************************************************************"
	@echo " *"
	@echo " * deploy           - builds and deploys the current version to the Sonatype OSS repository"
	@echo " * deploy-site      - builds and deploys the documentation site"
	@echo " * release          - create and deploy a Jdbi release"
