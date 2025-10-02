package com.sistdist.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota para la exclusión mutua.
 * Define los métodos que los clientes pueden invocar en el servidor.
 */
public interface IExclusionMutua extends Remote {
    
    /**
     * Solicita acceso exclusivo a un recurso.
     * @param recurso nombre del recurso (ej: "valvula1")
     * @param clienteId identificador del cliente solicitante
     * @return true si el acceso fue concedido, false si está ocupado
     */
    boolean solicitarAcceso(String recurso, String clienteId) throws RemoteException;

    /**
     * Libera el acceso a un recurso previamente ocupado.
     * @param recurso nombre del recurso
     * @param clienteId identificador del cliente que libera
     */
    void liberarAcceso(String recurso, String clienteId) throws RemoteException;

    /**
     * Consulta quién tiene actualmente el recurso.
     * @param recurso nombre del recurso
     * @return ID del cliente que posee el recurso, o "LIBRE" si no está ocupado
     */
    String consultarEstado(String recurso) throws RemoteException;
}
