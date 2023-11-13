package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BicicletaRepository implements Repository<Bicicleta, Long> {

    private ClienteRepository clienteRepository = ClienteRepository.build();

    private ConnectionFactory factory;

    private static final AtomicReference<BicicletaRepository> instance = new AtomicReference<>();

    private BicicletaRepository() {
        this.factory = ConnectionFactory.build();
    }

    public static BicicletaRepository build() {
        instance.compareAndSet( null, new BicicletaRepository() );
        return instance.get();
    }

    @Override
    public List<Bicicleta> findAll() {

        List<Bicicleta> bicicletas = new ArrayList<>();

        var sql = """
                SELECT * FROM
                TB_BICICLETA
                """;

        Connection conn = factory.getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {

            st = conn.createStatement();
            rs = st.executeQuery( sql );

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    var id = rs.getLong( "ID_BICICLETA" );
                    var descricao = rs.getString( "TITLE" );
                    var cliente = clienteRepository.findById( idCliente );
                    bicicletas.add( new Bicicleta( id, descricao, cliente ) );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, st, conn );
        }

        return bicicletas;
    }

    @Override
    public Bicicleta findById(Long id) {

        Bicicleta bicicleta = null;

        var sql = """
                SELECT * 
                FROM TB_BICICLETA
                WHERE ID_BICICLETA = ?
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

                    var idBicicleta = rs.getLong( "ID_BICICLETA" );
                    var descricao = rs.getString( "TITLE" );
                    var idCliente = rs.getLong( "GENRE" );
                    var cliente = clienteRepository.findById( idCliente );
                    bicicleta = new Bicicleta( idBicicleta, descricao, cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }

        return bicicleta;
    }

    @Override
    public Bicicleta persist(Bicicleta bicicleta) {

        var sql = """
                INSERT INTO TB_BICICLETA
                (ID_BICICLETA, TITLE, ADULT, ORIGINALLANGUAGE, GENRE )
                VALUES
                (SQ_BICICLETA.nextval, ?,?,?,?)
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement( sql, new String[]{"ID_BICICLETA"} );
            ps.setString( 1, bicicleta.getDescricao() );
            ps.setLong( 4, bicicleta.getCliente().getId() );
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                final Long id = rs.getLong( 1 );
                bicicleta.setId( id );
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possivel salvar a Bicicleta no banco de dados:  " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return bicicleta;
    }

    @Override
    public Bicicleta findByName(String texto) {

        Bicicleta bicicleta = null;

        var sql = """
                SELECT * 
                FROM TB_BICICLETA
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
                    var idBicicleta = rs.getLong( "ID_BICICLETA" );
                    var idCliente = rs.getLong( "GENRE" );
                    var cliente = clienteRepository.findById( idCliente );
                    bicicleta = new Bicicleta( idBicicleta, cliente );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return bicicleta;
    }
}
