/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sistemaf.sistemafertirrigacion;
import Interfaces.IExclusionMutua;

import java.rmi.Naming;
import java.rmi.*;

/**
 *
 * @author Anita
 */
public class SistemaFertirrigacion {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        try {
            String clienteId = args.length > 0 ? args[0] : "SistFerti";
            String recurso = "valvula1";

            IExclusionMutua exclusion = (IExclusionMutua) Naming.lookup("rmi://localhost:9000/ExclusionMutua");

            // Solicitar acceso
            if (exclusion.solicitarAcceso(recurso, clienteId)) {
                System.out.println(clienteId + " obtuvo acceso a " + recurso);

                // Simular trabajo con el recurso
                Thread.sleep(5000);

                // Liberar acceso
                exclusion.liberarAcceso(recurso, clienteId);
                System.out.println(clienteId + " liberó el recurso " + recurso);
            } else {
                System.out.println(clienteId + " NO pudo acceder al recurso " + recurso);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
