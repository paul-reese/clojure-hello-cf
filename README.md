# hello-cf

A Clojure RESTful Web Service that can be deployed to Cloud Foundry using a Heroku Clojure or Java Buildpack. This project is taken from [Matt Stine's Clojure Blog] (http://www.mattstine.com/2013/05/29/clojure-on-cloud-foundry/).


## Pre-requisites

To use this project to push the support Clojure RESTful application to Cloud Foundry the user must;
* [Download and Install Cloud Foundry CLI] ()
* [Install Lein] (http://leiningen.org/#install "for Mac OSX")

## Enable Clojure App for Cloud Foundry

To enable this Clojure application to run in Cloud Foundry a Profile was created in the root directory containing this snippit
    
    web: lein with-profile production trampoline run -m hello-cf.core $PORT

## Create an Uberjar

If you'd like to fork and make changes one would need to create an uberjar using the lein command
    
    lein uberjar

The necessary artifacts (including a standalone JAR file) will be created
    
    $ ls -ltr target                                                                                                                        1 ↵
    total 11192
    drwxr-xr-x  3 phopper  staff      102 Jul 19 12:20 stale
    drwxr-xr-x  5 phopper  staff      170 Jul 19 12:20 classes
    -rw-r--r--  1 phopper  staff    56364 Jul 19 12:20 hello-cf-0.1.0-SNAPSHOT.jar
    -rw-r--r--  1 phopper  staff  5670274 Jul 19 12:20 hello-cf-0.1.0-SNAPSHOT-standalone.jar    

## Usage

### To run locally

    % lein trampoline run -m hello-cf.core 8080

Application will retrieve its dependencies

    Retrieving environ/environ/1.0.3/environ-1.0.3.pom from clojars
    Retrieving ring/ring-devel/1.4.0/ring-devel-1.4.0.pom from clojars
    Retrieving ring/ring-core/1.4.0/ring-core-1.4.0.pom from clojars
    .
    .
    .
    2016-07-25 15:58:03.716:INFO:oejs.Server:jetty-7.6.1.v20120215
    2016-07-25 15:58:03.751:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:8080

User only needs to open 0.0.0.0:8080 in a browser or execute a curl GET aginast the endpoint

    % curl 0.0.0.0:8080

    Hello people, this is a Clojure app running with java buildpack!   

### To run on Cloud Foundry

There are two Cloud Foundry manifest files provided;

* manifest.yml - supports using a Heroku Clojure OSS buildpack
* manifest-java.yml - supports using the Java OSS buildpack

To cf push application using Heroku Clojure Buildpack

    % cf push -f manifest.yml

    Using manifest file manifest.yml

    Updating app hello-cf-heroku-clojure in org Vertical / space development as phopper@pivotal.io...
    OK

    Using route hello-cf-heroku-clojure.cfapps.io
    Uploading hello-cf-heroku-clojure...
    Uploading app files from: /Users/phopper/Documents/pivotal/workspace/clojure/hello-cf
    Uploading 167.4K, 75 files
    Done uploading               
    OK

    Starting app hello-cf-heroku-clojure in org Vertical / space development as phopper@pivotal.io...
    Creating container
    Successfully created container
    Downloaded app package (4.9M)
    Downloading build artifacts cache...
    Downloading app package...
    Downloaded build artifacts cache (18M)
    Staging...
    -----> Installing OpenJDK 1.8... done
    -----> Using cached Leiningen 2.6.1
        Writing: lein script
    -----> Building with Leiningen
        Running: lein with-profile production compile :all
        "Retrieving" "environ/environ/1.0.3/environ-1.0.3.pom" "from" "clojars"
        "Retrieving" "org/clojure/clojure/1.5.1/clojure-1.5.1.pom" "from" "central"
        "Retrieving" "org/sonatype/oss/oss-parent/5/oss-parent-5.pom" "from" "central"
        "Retrieving" "environ/environ/1.0.3/environ-1.0.3.jar" "from" "clojars"
        Compiling hello-cf.core
    Exit status 0
    Staging complete
    Uploading droplet, build artifacts cache...
    Uploading build artifacts cache...
    Uploading droplet...
    Uploaded build artifacts cache (18M)
    Uploaded droplet (70.9M)
    Uploading complete
    Destroying container
    Successfully destroyed container

    0 of 1 instances running, 1 starting
    1 of 1 instances running

    App started


    OK

    App hello-cf-heroku-clojure was started using this command `lein with-profile production trampoline run -m hello-cf.core $PORT`

    Showing health and status for app hello-cf-heroku-clojure in org Vertical / space development as phopper@pivotal.io...
    OK

    requested state: started
    instances: 1/1
    usage: 512M x 1 instances
    urls: hello-cf-heroku-clojure.cfapps.io
    last uploaded: Mon Jul 25 22:03:59 UTC 2016
    stack: unknown
    buildpack: https://github.com/heroku/heroku-buildpack-clojure.git

        state     since                    cpu    memory          disk           details
    #0   running   2016-07-25 04:04:38 PM   0.0%   21.7M of 512M   161.7M of 1G

To cf push application using Java Buildpack

    % cf push -f manifest-java.yml

    Using manifest file manifest-java.yml

    Updating app hello-cf-java in org Vertical / space development as phopper@pivotal.io...
    OK

    Using route hello-cf-java.cfapps.io
    Uploading hello-cf-java...
    Uploading app files from: /var/folders/hz/_9jdwmpn39n1dmyfj2nlw0880000gn/T/unzipped-app427249576
    Uploading 10.6M, 5053 files
    Done uploading               
    OK

    Stopping app hello-cf-java in org Vertical / space development as phopper@pivotal.io...
    OK

    Starting app hello-cf-java in org Vertical / space development as phopper@pivotal.io...
    Creating container
    Successfully created container
    Downloading app package...
    Downloaded app package (5.6M)
    Downloading build artifacts cache...
    Downloaded build artifacts cache (44.2M)
    Staging...
    -----> Java Buildpack Version: 3ac086c | https://github.com/cloudfoundry/java-buildpack#3ac086c
    -----> Downloading Open Jdk JRE 1.8.0_91-unlimited-crypto from https://java-buildpack.cloudfoundry.org/openjdk/trusty/x86_64/openjdk-1.8.0_91-unlimited-crypto.tar.gz (found in cache)
        Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.0s)
    -----> Downloading Open JDK Like Memory Calculator 2.0.2_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/trusty/x86_64/memory-calculator-2.0.2_RELEASE.tar.gz (found in cache)
        Memory Settings: -Xss228K -Xmx317161K -XX:MaxMetaspaceSize=64M -Xms317161K -XX:MetaspaceSize=64M
    Exit status 0
    Staging complete
    Uploading droplet, build artifacts cache...
    Uploading build artifacts cache...
    Uploading droplet...
    Uploaded build artifacts cache (44.2M)
    Uploaded droplet (47.8M)
    Uploading complete
    Destroying container
    Successfully destroyed container

    0 of 1 instances running, 1 starting
    1 of 1 instances running

    App started


    OK

    App hello-cf-java was started using this command `CALCULATED_MEMORY=$($PWD/.java-buildpack/open_jdk_jre/bin/java-buildpack-memory-calculator-2.0.2_RELEASE -memorySizes=metaspace:64m..,stack:228k.. -memoryWeights=heap:65,metaspace:10,native:15,stack:10 -memoryInitials=heap:100%,metaspace:100% -stackThreads=300 -totMemory=$MEMORY_LIMIT) && JAVA_OPTS="-Djava.io.tmpdir=$TMPDIR -XX:OnOutOfMemoryError=$PWD/.java-buildpack/open_jdk_jre/bin/killjava.sh $CALCULATED_MEMORY -Djava.security.egd=file:///dev/urandom" &&  eval exec $PWD/.java-buildpack/open_jdk_jre/bin/java $JAVA_OPTS -cp $PWD/. hello_cf.core`

    Showing health and status for app hello-cf-java in org Vertical / space development as phopper@pivotal.io...
    OK

    requested state: started
    instances: 1/1
    usage: 512M x 1 instances
    urls: hello-cf-java.cfapps.io
    last uploaded: Mon Jul 25 22:07:22 UTC 2016
    stack: unknown
    buildpack: https://github.com/cloudfoundry/java-buildpack

        state     since                    cpu    memory           disk         details
    #0   running   2016-07-25 04:07:58 PM   0.0%   175.6M of 512M   146M of 1G