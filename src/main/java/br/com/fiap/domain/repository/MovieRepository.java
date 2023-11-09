package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Movie;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MovieRepository implements Repository<Movie, Long> {


    private GenreRepository genreRepository = GenreRepository.build();


    private ConnectionFactory factory;

    private static final AtomicReference<MovieRepository> instance = new AtomicReference<>();

    private MovieRepository() {
        this.factory = ConnectionFactory.build();
    }

    public static MovieRepository build() {
        instance.compareAndSet( null, new MovieRepository() );
        return instance.get();
    }


    @Override
    public List<Movie> findAll() {

        List<Movie> filmes = new ArrayList<>();

        var sql = """
                SELECT * FROM
                TB_MOVIE
                """;

        Connection conn = factory.getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {

            st = conn.createStatement();
            rs = st.executeQuery( sql );

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    var id = rs.getLong( "ID_MOVIE" );
                    var title = rs.getString( "TITLE" );
                    var adulto = rs.getBoolean( "ADULT" );
                    var language = rs.getString( "ORIGINALLANGUAGE" );
                    var idGenre = rs.getLong( "GENRE" );
                    var genre = genreRepository.findById( idGenre );
                    filmes.add( new Movie( id, title, adulto, genre, language ) );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, st, conn );
        }

        return filmes;
    }

    @Override
    public Movie findById(Long id) {
        return null;
    }

    @Override
    public Movie persist(Movie movie) {
        return null;
    }
}
