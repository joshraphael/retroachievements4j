#!/bin/bash

export VERSION="0.0.0"

if [[ ${GITHUB_REF_NAME} != "" ]]; then
    VERSION="${GITHUB_REF_NAME#v}"
fi

cat templates/gradle.properties.template | envsubst > gradle.properties