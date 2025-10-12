SHELL := /bin/bash

setup:
	bash scripts/setup.sh

test: setup
	./gradlew test

publish: setup
	./gradlew publish