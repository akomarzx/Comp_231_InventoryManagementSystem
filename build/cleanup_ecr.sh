#!/bin/bash

IMAGES_TO_DELETE=$( aws ecr list-images --region us-east-1 --repository-name lyfeapp --filter "tagStatus=UNTAGGED" --query 'imageIds[*]' --output json )
aws ecr batch-delete-image --region us-east-1 --repository-name lyfeapp --image-ids "$IMAGES_TO_DELETE" || true
