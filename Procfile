web:    java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
scheduler: java $JAVA_OPTS -cp target/classes:target/dependency/* com.heroku.devcenter.SchedulerMain
worker: java $JAVA_OPTS -cp target/classes:target/dependency/* com.heroku.devcenter.WorkerMain