#!/bin/bash

export VERSION="v0.0.0"

if [[ ${GITHUB_REF_NAME} != "" ]]; then
    VERSION="$GITHUB_REF_NAME"
fi

cat templates/gradle.properties.template | envsubst > gradle.properties