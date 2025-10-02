package sistemaf.sistemafertirrigacion;

import com.sistdist.interfaces.IExclusionMutua;

import java.rmi.Naming;
import java.rmi.NotBoundException;

public class SistemaFertirrigacion {

    private static final String CLIENTE_ID = "SistemaFertirrigacion";
    private static final String RECURSO_BOMBA = "BombaAgua";

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Fertirrigación...");

        try {
            // Conectar al servicio de exclusión mutua
            IExclusionMutua exclusion = (IExclusionMutua) Naming.lookup("rmi://localhost:9000/ExclusionMutua");
            System.out.println("Conexión establecida con el servidor de Exclusión Mutua.");

            while (true) {
                System.out.println("\n[" + CLIENTE_ID + "] -> Intentando iniciar ciclo de fertirrigación.");

                // Solicitar acceso exclusivo al recurso compartido
                if (exclusion.solicitarAcceso(RECURSO_BOMBA, CLIENTE_ID)) {
                    System.out.println("[" + CLIENTE_ID + "] -> Acceso a '" + RECURSO_BOMBA + "' CONCEDIDO. Iniciando fertirrigación.");
                    try {
                        // Simular trabajo con el recurso (el proceso de fertirrigación)
                        System.out.println("[" + CLIENTE_ID + "] -> Proceso de fertirrigación en curso... (Duración: 10 segundos)");
                        Thread.sleep(10000);
                        System.out.println("[" + CLIENTE_ID + "] -> Proceso de fertirrigación FINALIZADO.");

                    } finally {
                        // Liberar el acceso al recurso, SIEMPRE
                        exclusion.liberarAcceso(RECURSO_BOMBA, CLIENTE_ID);
                        System.out.println("[" + CLIENTE_ID + "] -> Recurso '" + RECURSO_BOMBA + "' LIBERADO.");
                    }
                } else {
                    // El recurso está ocupado por otro sistema (probablemente el controlador de riego)
                    String propietarioActual = exclusion.consultarEstado(RECURSO_BOMBA);
                    System.out.println("[" + CLIENTE_ID + "] -> Acceso a '" + RECURSO_BOMBA + "' DENEGADO. Ocupado por: " + propietarioActual);
                }
                // Esperar un tiempo antes de intentar el siguiente ciclo
                System.out.println("[" + CLIENTE_ID + "] -> Esperando 20 segundos para el próximo ciclo.");
                Thread.sleep(20000);

            }

        } catch (NotBoundException e) {
            System.err.println("Error: El servicio de Exclusión Mutua no está registrado en el servidor RMI.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error en el Sistema de Fertirrigación:");
            e.printStackTrace();
        }
    }
}