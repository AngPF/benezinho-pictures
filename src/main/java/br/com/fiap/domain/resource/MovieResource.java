package br.com.fiap.domain.resource;

import br.com.fiap.domain.dto.MovieDTO;
import br.com.fiap.domain.entity.Movie;
import br.com.fiap.domain.service.MovieService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource implements Resource<MovieDTO, Long> {

    @Context
    UriInfo uriInfo;

    private final MovieService service = new MovieService();

    @GET
    @Override
    public Response findAll() {
        List<Movie> all = service.findAll();
        return Response.ok( all.stream().map( MovieDTO::of ).toList() ).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {
        Movie filme = service.findById( id );
        if (Objects.isNull( filme )) return Response.status( 404 ).build();
        return Response.ok( MovieDTO.of( filme ) ).build();
    }


    @POST
    @Override
    public Response persist(MovieDTO movie) {

        Movie persisted = service.persist( MovieDTO.of( movie ) );

        if (Objects.isNull( persisted )) return Response.status( 400 ).build();

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( String.valueOf( persisted.getId() ) ).build();

        return Response.created( uri ).entity( MovieDTO.of( persisted ) ).build();
    }
}
