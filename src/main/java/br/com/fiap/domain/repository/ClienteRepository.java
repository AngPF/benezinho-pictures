package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClienteRepository implements Repository<Cliente, Long> {


    private ConnectionFactory factory;

    private static final AtomicReference<ClienteRepository> instance = new AtomicReference<>();

    private ClienteRepository() {
        this.factory = ConnectionFactory.build();
    }

    public static ClienteRepository build() {
        instance.compareAndSet( null, new ClienteRepository() );
        return instance.get();
    }


    @Override
    public List<Cliente> findAll() {

        List<Cliente> clientes = new ArrayList<>();

        var sql = """
                SELECT * 
                FROM TB_CLIENTE               
                """;

        Connection conn = factory.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery( sql );
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    var id = rs.getLong( "ID_CLIENTE" );
                    var name = rs.getString( "NM_CLIENTE" );
                    clientes.add( new Cliente( id, name ) );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, st, conn );
        }
        return clientes;
    }

    @Override
    public Cliente findById(Long id) {

        Cliente cliente = null;

        var sql = """
                SELECT *
                FROM TB_CLIENTE
                WHERE ID_CLIENTE = ?
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
                    var idCliente = rs.getLong( "ID_CLIENTE" );
                    var name = rs.getString( "NM_CLIENTE" );
                    cliente = new Cliente( idCliente, name );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return cliente;
    }

    @Override
    public Cliente persist(Cliente cliente) {

        var sql = """
                INSERT INTO TB_CLIENTE (ID_CLIENTE, NM_CLIENTE)
                values
                (SQ_CLIENTE.nextval,?)
                """;

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement( sql,  new String[] { "ID_CLIENTE" } );
            ps.setString( 1, cliente.getName() );
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId( rs.getLong( 1 ) );
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível salvar no banco de dados: " + e.getMessage() + "\n" + e.getCause() + "\n" + e.getErrorCode() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return cliente;
    }

    @Override
    public Cliente findByName(String texto) {

        Cliente cliente = null;

        var sql = """
                SELECT *
                FROM TB_CLIENTE
                WHERE trim(upper(NM_CLIENTE)) = ?
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
                    var idCliente = rs.getLong( "ID_CLIENTE" );
                    var name = rs.getString( "NM_CLIENTE" );
                    cliente = new Cliente( idCliente, name );
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        } finally {
            fecharObjetos( rs, ps, conn );
        }
        return cliente;
    }
}
