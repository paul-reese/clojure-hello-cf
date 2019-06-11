# hello-cf

A Clojure RESTful Web Service that can be deployed to Cloud Foundry using a Heroku Clojure or Java Buildpack. This project is taken from [Matt Stine's Clojure Blog] (http://www.mattstine.com/2013/05/29/clojure-on-cloud-foundry/).


## Pre-requisites

To use this project to push the support Clojure RESTful application to Cloud Foundry the user must;
* [Download and Install Cloud Foundry CLI] ()
* [Install Lein] (http://leiningen.org/#install "for Mac OSX")

## Enable Clojure App for Cloud Foundry

To enable this Clojure application to run in Cloud Foundry a Profile was created in the root directory. The web profile or Procfile will reside in the root directory of the hello_cf project. To view this Procfile;
    
    % more Procfile
    web: lein with-profile production trampoline run -m hello-cf.core $PORT

## Create an Uberjar

If you'd like to fork and make changes one would need to create an uberjar using the lein command. This is required for one to push application to PCF.
    
    % lein uberjar
    Compiling hello-cf.core
    Compiling hello-cf.core
    Created /Users/phopper/Documents/pivotal/workspace/clojure/hello-cf/target/hello-cf-0.1.0-SNAPSHOT.jar
    Created /Users/phopper/Documents/pivotal/workspace/clojure/hello-cf/target/hello-cf-0.1.0-SNAPSHOT-standalone.jar

You will see here that two JAR files are created;
* a base JAR
* a standalone JAR

We can use either for our cf push to Cloud Foundry 

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

The recommended way to run Clojure applications on Cloud Foundry is to use the Pivotal supported Open Source Java Buildpack. Pivotal will support this Buildpack providing regular updates (version against OpenJDK, Tomcat, etc) as well as any CVE's should they arise. The process outlined here will follow this path;

* manifest.yml - supports using OSS Java Buildpack

To cf push application using Java Buildpack

    % cf push -f manifest.yml

    Using manifest file manifest.yml

    Updating app hello-cf-java in org Vertical / space development as phopper@pivotal.io...
    OK

    Creating route hello-cf-java-flawless-sandhi.cfapps.io...
    OK

    Binding hello-cf-java-flawless-sandhi.cfapps.io to hello-cf-java...
    OK

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
    urls: hello-cf-java-flawless-sandhi.cfapps.io
    last uploaded: Mon Jul 25 22:07:22 UTC 2016
    stack: unknown
    buildpack: https://github.com/cloudfoundry/java-buildpack

        state     since                    cpu    memory           disk         details
    #0   running   2016-07-25 04:07:58 PM   0.0%   175.6M of 512M   146M of 1G
