
Run mongo db in docker image

    docker run --rm --network=ms-exp-bridge -p=27017:27017 --name=alarm-inv-mongo

Run docker image with alarm inv app




Or use docker-compose

    docker-compose -f docker-compose.yml


to retrieve alarms

   curl -i -X GET "http://localhost:8090/alarminventory/alarmdefinitions"
   curl -i -X GET "http://localhost:8090/alarminventory/alarms"