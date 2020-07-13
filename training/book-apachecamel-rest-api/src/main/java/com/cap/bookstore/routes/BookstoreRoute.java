package com.cap.bookstore.routes;

import com.cap.bookstore.model.Book;
import com.cap.bookstore.model.BookAsyncResponse;
import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
public class BookstoreRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Value("${camel.component.servlet.mapping.contextPath}")
    private String contextPath;

    @Override
    public void configure() throws Exception {

        //GsonDataFormat custEx = new GsonDataFormat(ErrorResponse.class);
        initializeExceptionHandler();
        restConfiguration()
                .component("servlet")
                .host("localhost")
                .port(8087)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

     //  GsonDataFormat bookFormat = new GsonDataFormat(Book.class);
        rest("/bookstore")
              .consumes(MediaType.APPLICATION_JSON_VALUE)
              .produces(MediaType.APPLICATION_JSON_VALUE)

              .get("/book/{isbn}")
                    .produces("application/json")
                    .param().name("isbn").type(RestParamType.path).description("The isbn of the book to get").dataType("string").endParam()
                    .to("direct:getBookByIsbnRoute")

              .get().outType(Book[].class)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:getBooksRoute")

              .post("book")
                    .type(Book[].class)
                    .outType(Book.class)
                    .consumes("application/json")
                    .produces("application/json")
                    .to("direct:bookMulticastRoute")

              .put("/book/{isbn}")
                    .type(Book.class)
                    .consumes("application/json")
                    .produces("application/json")
                    .param().name("isbn").type(RestParamType.path).description("The isbn of the book to update").dataType("string").endParam()
                    .to("direct:updateBookRoute")

            /*  .post("/book")
                    .type(Book[].class)
                    .consumes("application/json")
                    .produces("application/json")
                    .param().name("body").type(RestParamType.body).description("The isbn of the book to add").endParam()
                    .to("direct:bookMulticastRoute") */

              .delete("/book/{isbn}")
                    .outType(BookResponse.class)
                    .produces("application/json")
                    .param().name("isbn").type(RestParamType.path).description("The isbn of the book to delete").dataType("string").endParam()
                    .to("direct:deleteBookRoute")

              .delete("/book/async/{isbn}?transactionId={transactionId}")
                    .outType(BookResponse.class)
                    .produces("application/json")
                    .param().name("isbn").type(RestParamType.path).description("The isbn of the book to delete").dataType("string").endParam()
                    .param().name("transactionId").type(RestParamType.query).description("Transaction Id").dataType("string").endParam()
                    .to("direct:deleteBookWireTapRoute")

              .delete("/books/async?transactionId={transactionId}&isbnList={isbnList}")
                    .outType(BookAsyncResponse.class)
                    .produces("application/json")
                    .param().name("isbnList").type(RestParamType.query).description("The isbn list of the books to delete").dataType("string").endParam()
                    .param().name("transactionId").type(RestParamType.query).description("Transaction Id").dataType("string").endParam()
                    .to("direct:asyncDeleteBookListRoute");
    }
}
