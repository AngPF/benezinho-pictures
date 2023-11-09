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

    @Override
    public Genre persist(Genre genre) {

        var sql = """
                BEGIN
                INSERT INTO (ID_GENRE, NM_GENRE)
                values
                (SQ_GENRE.nextval,?)
                returning ID_GENRE into ?;  END;
                """;

        Connection conn = factory.getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall( sql );
            cs.setString( 1, genre.getName() );
            cs.registerOutParameter( 2, Types.BIGINT );
            cs.executeUpdate();
            genre.setId( cs.getLong( 2 ) );
        } catch (SQLException e) {
            System.err.println( "Não foi possível salvar no banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( null, cs, conn );
        }
        return genre;
    }
}
