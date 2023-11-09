package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Movie;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.*;
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

        Movie movie = null;

        var sql = """
                SELECT * 
                FROM TB_MOVIE
                WHERE ID_MOVIE = ?
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement( sql );
            ps.setLong( 1, id );
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    var idMovie = rs.getLong( "ID_MOVIE" );
                    var title = rs.getString( "TITLE" );
                    var adulto = rs.getBoolean( "ADULT" );
                    var language = rs.getString( "ORIGINALLANGUAGE" );
                    var idGenre = rs.getLong( "GENRE" );
                    var genre = genreRepository.findById( idGenre );
                    movie = new Movie( idMovie, title, adulto, genre, language );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }

        return movie;
    }

    /**
     * Mostrando uma nova forma de persistir.
     * <p>
     * Funciona em todos os SGBDR
     *
     * @param movie
     * @return
     */
    @Override
    public Movie persist(Movie movie) {

        var sql = """
                INSERT INTO TB_MOVIE
                (ID_MOVIE, TITLE, ADULT, ORIGINALLANGUAGE, GENRE )
                VALUES
                (SQ_MOVIE.nextval, ?,?,?,?)
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement( sql, new String[]{"ID_MOVIE"} );
            ps.setString( 1, movie.getTitle() );
            ps.setBoolean( 2, movie.isAdult() );
            ps.setString( 3, movie.getOriginalLanguage() );
            ps.setLong( 4, movie.getGenre().getId() );
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                final Long id = rs.getLong( 1 );
                movie.setId( id );
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possivel salvar o Movie no banco de dados:  " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return movie;
    }

    @Override
    public Movie findByName(String texto) {

        Movie movie = null;

        var sql = """
                SELECT * 
                FROM TB_MOVIE
                WHERE trim(UPPER(TITLE)) = ?
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement( sql );
            ps.setString( 1, texto.toUpperCase().trim() );
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    var idMovie = rs.getLong( "ID_MOVIE" );
                    var title = rs.getString( "TITLE" );
                    var adulto = rs.getBoolean( "ADULT" );
                    var language = rs.getString( "ORIGINALLANGUAGE" );
                    var idGenre = rs.getLong( "GENRE" );
                    var genre = genreRepository.findById( idGenre );
                    movie = new Movie( idMovie, title, adulto, genre, language );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return movie;
    }
}
