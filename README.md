Rest Queue Router
--------------------------
>Rest queue router prototype.

>NOTE: This prototype does not use a single queue connection nor a pool
of queue connections. Intead, it creates a new queue connection just-in-time
in anticipation of targeting a RabbitMQ cluster! Read this posts for more
insights:

* http://stackoverflow.com/questions/10407760/is-there-a-performance-difference-between-pooling-connections-or-channels-in-rab
* https://www.rabbitmq.com/blog/2011/09/24/sizing-your-rabbits/

Install
-------
1. brew install RabbitMQ

Start
-----
1. brew services start rabbitmq

Stop
----
1. brew services stop rabbitmq

Test
----
1. sbt clean it:test

Pack
----
1. sbt clean compile it:test pack

Config
------
> The following configuration file:

Run
---
>Run QueueRouterApp via sbt:

1. sbt run

>Run RestQueueRouterApp via pack:

1. ./target/pack/bin/queue-router-app

Log
---
>The app log is written to: ./log/app.queue.router.log

RabbitMQ
--------
>See rabbitmqadmin @ https://www.rabbitmq.com/management-cli.html

>See rabbitmqctl @ https://www.rabbitmq.com/man/rabbitmqctl.1.man.html

>List

1. rabbitmqctl list_queues name messages_ready messages_unacknowledged

>Reset

1. rabbitmqctl stop_app
2. rabbitmqctl reset
3. rabbitmqctl start_app