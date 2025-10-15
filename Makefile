SHELL := /bin/bash

setup:
	bash scripts/setup.sh

test: setup
	./gradlew test

publish-github: setup
	./gradlew publish

publish-central: setup
	./gradlew publishToMavenCentral