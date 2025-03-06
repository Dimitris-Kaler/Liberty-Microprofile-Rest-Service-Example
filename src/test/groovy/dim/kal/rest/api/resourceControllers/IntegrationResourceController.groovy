package dim.kal.rest.api.resourceControllers

import dim.kal.rest.models.Greeting
import groovy.json.JsonSlurper
import jakarta.json.bind.Jsonb
import jakarta.json.bind.JsonbBuilder
import spock.lang.Shared
import spock.lang.Specification

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class IntegrationResourceController extends Specification {
    static final String LIBERTY_REST_EXAMPLE_URL = "http://localhost:9080/rest-service-example/api/"

    @Shared
    HttpClient client

    @Shared
    Jsonb jsonb

    def setup() {
        client = HttpClient.newHttpClient()  // Initialize the HttpClient
        jsonb = JsonbBuilder.create()        // Correct way to initialize Jsonb using JsonbBuilder
    }

    def "200: successfully submit a request"() {
        given:
        def request = createGetRequest()

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        jsonResponse.statusCode() == 200

        and:
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        println "Response message: ${responseBody.msg}"
        responseBody.msg == "Hello World!"


    }

    def "200 succesfull response with path params request"(){
        given:
        def name = "John"
        and:
        def request = createGetRequestWithPathParams(name)

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        jsonResponse.statusCode() == 200;

        and:
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        println "Response message: ${responseBody.msg}"
        responseBody.msg == "Hello John!"
    }

    def "400 BAD Request response with invalid path or query params"(){

        given:
        def request = createGetRequestWithInvalidParams()

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        jsonResponse.statusCode() == 400;

        and:
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        println "Response message: ${responseBody.msg}"
        responseBody.msg == "Name or age can't be null"
    }

    def"200 succesfull request with query params"(){
        given:
        def name ="Xantakias"
        def age =33
        and:
        def request = createGetRequestWithQueryPrams(name,age)

        when:
        def jsonResponse = client.send(request,HttpResponse.BodyHandlers.ofString())
        then:
        jsonResponse.statusCode() == 200

        and:
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        println "Response message: ${responseBody.msg}"
        responseBody.msg == "Hello my name is: Xantakias and my age is: 33 old!!"

    }

    def "200:succesfull request with bodyReq"(){
        given:
        def bodyReq = new Greeting()
        bodyReq.setName("Xantakias")
        bodyReq.setAge(33)

        and:
        def request = createPostRequest(bodyReq)

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())


        then: 'the response is successful'
        jsonResponse.statusCode() == 200

        and:'The expected msg'
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        responseBody.msg == "Hello my name is: Xantakias and my age is: 33 old!!"

    }


    def "400 :BAD request with bodyReq.age setted null"(){
        def bodyReq = new Greeting()
        bodyReq.setName("Xantakias")
        bodyReq.setAge(null)
        def request = createPostRequest(bodyReq)

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())


        then: 'the response is successful'
        jsonResponse.statusCode() == 400

        and:'The expected msg'
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        responseBody.msg == "Name or age can't be null"

    }


    def "400 :BAD request with bodyReq.name setted null"(){
        def bodyReq = new Greeting()
        bodyReq.setName(null)
        bodyReq.setAge(23)
        def request = createPostRequest(bodyReq)

        when:
        def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())


        then: 'the response is successful'
        jsonResponse.statusCode() == 400

        and:'The expected msg'
        def responseBody = new JsonSlurper().parseText(jsonResponse.body())
        responseBody.msg == "Name or age can't be null"

    }




    // Correctly declare the HttpRequest creation method
    def createGetRequest() {
        HttpRequest.newBuilder()
                .uri(URI.create(LIBERTY_REST_EXAMPLE_URL))  // URI for the GET request
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build()
    }
    def createGetRequestWithPathParams(name){
        def URL_WITH_PATH_PARAMS = "http://localhost:9080/rest-service-example/api/greet/"+name
        HttpRequest.newBuilder()
                .uri(URI.create(URL_WITH_PATH_PARAMS))  // URI for the GET request
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build()


    }

    def createGetRequestWithInvalidParams(name){
        def URL_WITH_PATH_PARAMS = "http://localhost:9080/rest-service-example/api/greet/"
        HttpRequest.newBuilder()
                .uri(URI.create(URL_WITH_PATH_PARAMS))  // URI for the GET request
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build()


    }

    def createGetRequestWithQueryPrams(name,age){
        def URL_WITH_QUERY_PARAMS = "http://localhost:9080/rest-service-example/api/greet?name="+name+"&age="+age
        HttpRequest.newBuilder()
                .uri(URI.create(URL_WITH_QUERY_PARAMS))  // URI for the GET request
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build()
    }

    def createPostRequest(bodyReq){
       String jsonData =jsonb.toJson(bodyReq)

        HttpRequest.newBuilder(URI.create("http://localhost:9080/rest-service-example/api/greet/"))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build()
    }
}
