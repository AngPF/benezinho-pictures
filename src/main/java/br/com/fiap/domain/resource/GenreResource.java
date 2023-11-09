package br.com.fiap.domain.resource;

import br.com.fiap.domain.dto.GenreDTO;
import br.com.fiap.domain.dto.MovieDTO;
import br.com.fiap.domain.entity.Genre;
import br.com.fiap.domain.service.GenreService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/genre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResource implements Resource<GenreDTO, Long> {

    @Context
    UriInfo uriInfo;

    private final GenreService service = new GenreService();

    @GET
    @Override
    public Response findAll() {
        List<Genre> all = service.findAll();
        return Response.ok( all.stream().map( GenreDTO::of ).toList() ).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {
        Genre genero = service.findById( id );
        if (Objects.isNull( genero )) return Response.status( 404 ).build();
        return Response.ok( GenreDTO.of( genero) ).build();
    }


    @POST
    @Override
    public Response persist(GenreDTO genre) {

        Genre persisted = service.persist(GenreDTO.of( genre) );

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(String.valueOf(persisted.getId())).build();

        return Response.created(uri).entity(GenreDTO.of(persisted)).build();
    }
}
