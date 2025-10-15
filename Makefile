SHELL := /bin/bash

setup:
	bash scripts/setup.sh

test: setup
	./gradlew test

publish-github: setup
	./gradlew publishAllPublicationsTogithubPackagesRepository

publish-central: setup
	./gradlew publishToMavenCentral