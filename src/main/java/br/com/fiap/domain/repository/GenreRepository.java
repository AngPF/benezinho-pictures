package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Genre;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GenreRepository implements Repository<Genre, Long> {


    private ConnectionFactory factory;

    private static final AtomicReference<GenreRepository> instance = new AtomicReference<>();

    private GenreRepository() {
        this.factory = ConnectionFactory.build();
    }

    public static GenreRepository build() {
        instance.compareAndSet( null, new GenreRepository() );
        return instance.get();
    }


    @Override
    public List<Genre> findAll() {

        List<Genre> generos = new ArrayList<>();

        var sql = """
                SELECT * 
                FROM TB_GENRE               
                """;

        Connection conn = factory.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery( sql );
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    var id = rs.getLong( "ID_GENRE" );
                    var name = rs.getString( "NM_GENRE" );
                    generos.add( new Genre( id, name ) );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, st, conn );
        }
        return generos;
    }

    @Override
    public Genre findById(Long id) {

        Genre genero = null;

        var sql = """
                SELECT *
                FROM TB_GENRE
                WHERE ID_GENRE = ?
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
                    var idGenre = rs.getLong( "ID_GENRE" );
                    var name = rs.getString( "NM_GENRE" );
                    genero = new Genre( idGenre, name );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return genero;
    }

    /**
     * Maneira que vinhamos persistindo.
     * Só funciona no ORACLE
     *
     * @param genre
     * @return
     */
    @Override
    public Genre persist(Genre genre) {

        var sql = """
                INSERT INTO TB_GENRE (ID_GENRE, NM_GENRE)
                values
                (SQ_GENRE.nextval,?)
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = conn.prepareStatement( sql,  new String[] { "ID_GENRE" } );
            ps.setString( 1, genre.getName() );


            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            ps.closeOnCompletion();
            if (rs.next()) {

                System.out.println( rs.getLong(1 ) );

                genre.setId( rs.getLong( 1 ) );
            }


        } catch (SQLException e) {
            System.err.println( "Não foi possível salvar no banco de dados: " + e.getMessage() + "\n" + e.getCause() + "\n" + e.getErrorCode() );
        } finally {
            fecharObjetos( null, ps, conn );
        }
        return genre;
    }

    @Override
    public Genre findByName(String texto) {

        Genre genero = null;

        var sql = """
                SELECT *
                FROM TB_GENRE
                WHERE trim(upper(NM_GENRE)) = ?
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
                    var idGenre = rs.getLong( "ID_GENRE" );
                    var name = rs.getString( "NM_GENRE" );
                    genero = new Genre( idGenre, name );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return genero;
    }
}
