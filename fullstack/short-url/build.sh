#!/bin/bash

# Verify that a MAJOR_VERSION has been supplied.
if [[ -z "$MAJOR_VERSION" ]] ; then
 echo "Required MAJOR_VERSION variable not defined."
 exit 1
fi

# Verify that a IMAGE_NAME has been supplied.
if [[ -z "$IMAGE_NAME" ]] ; then
 echo "Required IMAGE_NAME variable not defined."
 exit 1
fi

# Verify that a APP_ID has been supplied.
if [[ -z "$APP_ID" ]] ; then
 echo "Required APP_ID variable not defined."
 exit 1
fi

GIT_COMMIT="`git rev-parse --short HEAD`"

BUILD_TAG_VERSION=$MAJOR_VERSION.${BRANCH_NAME:0:1}.$GIT_COMMIT.$BUILD_ID

IMAGE_FULL_NAME="fcr-nonprod.ffmmrr.com/ffmmrr-$APP_ID/$IMAGE_NAME"
BUILD_TAG="$IMAGE_FULL_NAME:$BUILD_TAG_VERSION"

STAGE_VERACODESONAR="veracodesonar"
STAGE_RELEASEBUILD="releasebuild"
TEST_BUILD_TAG="$BUILD_TAG_VERSION-$STAGE_VERACODESONAR"

# Ensure we run our build with this directory as the context.
cd $(dirname $0)

# Build veracodesonar stage.
echo "Executing: docker build --pull --target $STAGE_VERACODESONAR -t $TEST_BUILD_TAG ."
docker build --pull --target $STAGE_VERACODESONAR -t $TEST_BUILD_TAG .
if [[ $? -ne 0 ]] ; then
 echo "docker build veracodesonar stage failed."
 exit 1
fi

CONTAINER_NAME="ffmmrr-$APP_ID-$IMAGE_NAME"
# Create container for Unit Test Coverage folder copy
echo "Creating $CONTAINER_NAME..."
docker rm -f $CONTAINER_NAME || true
docker create --name $CONTAINER_NAME $TEST_BUILD_TAG
if [ $? -ne 0 ] ; then
    echo "docker $CONTAINER_NAME create	 failed."
    exit 1
fi

echo "Copy Coverage folder from container to build Workspace"
docker cp $CONTAINER_NAME:/apps/coverage ${WORKSPACE}/

# replace source file path for sonar report
sed -i "s|/apps/||g" ${WORKSPACE}/coverage/overall/lcov.info

if [ $? -ne 0 ] ; then
    echo "docker cp /apps/coverage failed."
    exit 1
fi

echo "Copy veracode zip from container to workspace"
echo "Executing: docker cp $CONTAINER_NAME:/apps/veracode.zip ${WORKSPACE}/"
docker cp $CONTAINER_NAME:/apps/veracode.zip ${WORKSPACE}/
if [ $? -ne 0 ] ; then
    echo "docker cp /apps/veracode.zip failed."
    exit 1
fi

echo "Removing $CONTAINER_NAME..."
docker rm $CONTAINER_NAME
if [ $? -ne 0 ] ; then
	echo "failed to remove docker $CONTAINER_NAME container."
else
	echo "docker $CONTAINER_NAME container sucessfully removed from slave."
fi

# Build and tag image.
echo "Executing: docker build --pull --target $STAGE_RELEASEBUILD -t $BUILD_TAG ."
docker build --pull --target $STAGE_RELEASEBUILD -t $BUILD_TAG .
if [[ $? -ne 0 ]] ; then
 echo "docker build release stage failed."
 exit 1
fi

# Push image only DOCKER_PUBLISH is defined
if [[ -n "$DOCKER_PUBLISH" ]] ; then
  echo "Pushing $BUILD_TAG to Artifactory..."
  docker push $BUILD_TAG
  if [[ $? -ne 0 ]] ; then
   echo "docker push failed."
   exit 1
  fi
fi

echo "Build/Tag/Push completed successfully"

# Remove all images with this name which is not the latest version
# 1. List all tags with this image
# 2. Exclude current build
# 3. Remove filtered image
docker images --format {{.Tag}} --filter "reference=$IMAGE_FULL_NAME:*"|grep -vx "$BUILD_TAG_VERSION"|xargs -I {} docker rmi "$IMAGE_FULL_NAME:{}"  || true