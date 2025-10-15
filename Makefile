SHELL := /bin/bash

setup-central:
	export MAVEN_PUBLISH=true && bash scripts/setup.sh

setup-github:
	export MAVEN_PUBLISH=false && bash scripts/setup.sh

test: setup-github
	./gradlew test

publish-github: setup-github
	./gradlew publish

publish-central: setup-central
	./gradlew publishToMavenCentral