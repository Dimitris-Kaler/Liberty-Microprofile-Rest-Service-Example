package dim.kal.rest.resourceControllers

import dim.kal.rest.models.Greeting
import dim.kal.rest.resourceControllers.RestController
import spock.lang.Specification

class ResourceSpec extends Specification {

    RestController controller

    // Setup controller before each test
    void setup() {
        controller = new RestController()
    }

    def "test GET endpoint"() {
        when:
        // Directly invoking the helloWorld method
        def response = controller.helloWorld()

        then:
        // Assert that the status code is 200 (OK)
        response.getStatus() == 200

        // Read the entity from the response, expecting a Greeting object
        def message = response.getEntity()

        // Assert that the message's content is correct
        message.getMsg() == "Hello World!"
    }

    def "test the api with path params greeting method"(){
        when:
        def response= controller.pathParamsGreeting(name)

        then:
        response.getStatus() == status
        and:
        response.getEntity().getMsg() == msg
        where:

        name ||status ||msg
        "Dimitris"|200|"Hello Dimitris!"
        "John"    |200|"Hello John!"
        null      |400| "The name can't be null"
    }

    def "test the api with queryparams greeting method"(){

        when:
        def response =controller.queryParamsGreeting(name,age)

        then:
        response.getStatus()== status;

        and:
        response.getEntity().getMsg() == msg

        where:

        name || age || status || msg
        "Dim"| 25   | 200      | "Hello my name is: Dim and my age is: 25 old!!"
        null|30|400|"Name or age can't be null"
        "Tom"|null|400|"Name or age can't be null"
    }


    def "test the api with bodyReq greeting method"(){
        given:
        def input = new Greeting()
        input.setName(name)
        input.setAge(age)
        when:
        def response =controller.bodyParams(input)

        then:
        response.getStatus()== status;

        and:
        response.getEntity().getMsg() == msg

        where:

        name || age || status || msg
        "Dim"| 25   | 200      | "Hello my name is: Dim and my age is: 25 old!!"
        null|30|400|"Name or age can't be null"
        "Tom"|null|400|"Name or age can't be null"
    }



}

