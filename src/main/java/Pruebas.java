
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;



public class Pruebas {
	private static EntityManagerFactory emf =Persistence.createEntityManagerFactory("Discografia");
    private static EntityManager em = emf.createEntityManager();

	public static void main(String[] args) {
		
		IniciarTablaCantantes();
		Cantante buscado=buscarCantantePorNombre("Taylor Swift");
		if(buscado!=null) System.out.println(buscado.toString());
		else System.out.println("Cantante no encontrado");
		eliminarCantante("Michael Jackson");
		añadirCantante(new Cantante("Michael Jackson", "Estadounidense", LocalDate.of(1964, 1, 1), Cantante.Estado.retirado));
		añadirAlbumaACantante(buscado,new Album("Taylor Swift",15,LocalTime.of(0, 53, 0),"Country",LocalDate.of(2006, 10, 24)));
		em.close();
		emf.close();
	}
	public static void IniciarTablaCantantes() {
		List<Cantante> listaCantantes=new ArrayList<Cantante>();
		listaCantantes.add(new Cantante("Taylor Swift", "Estadounidense", LocalDate.of(2006, 10, 24), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Ariana Grande",  "Estadounidense",  LocalDate.of(2013, 3, 26), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Lady Gaga", "Estadounidense",  LocalDate.of(2008, 4, 8), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Johnny Orlando", "Canadiense",  LocalDate.of(2015, 12, 15), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Alec Benjamin", "Estadounidense",  LocalDate.of(2013, 11, 1), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Troye Sivan", "Australiano",  LocalDate.of(2007, 6, 1), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Olivia Rodrigo", "Estadounidense",  LocalDate.of(2021, 1, 8), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Charli XCX", "Británica",  LocalDate.of(2008, 5, 1), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Dua Lipa", "Británica",  LocalDate.of(2015, 8, 18), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Harry Styles", "Británico",  LocalDate.of(2010, 7, 23), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Alessandra Mele", "Noruega", LocalDate.of(2022, 11, 5), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Ed Sheeran", "Británico",  LocalDate.of(2011, 4, 26), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Kyle Alessandro", "Noruego",  LocalDate.of(2017, 1, 1), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Sabrina Carpenter", "Estadounidense", LocalDate.of(2014, 3, 14), Cantante.Estado.enActivo));
		listaCantantes.add(new Cantante("Michael Jackson", "Estadounidense", LocalDate.of(1964, 1, 1), Cantante.Estado.retirado));
		listaCantantes.add(new Cantante("Ross Lynch", "Estadounidense",  LocalDate.of(2012, 4, 2), Cantante.Estado.enActivo));
		
        try {
            for(Cantante cantante:listaCantantes) {
            	em.getTransaction().begin();
            	em.persist(cantante);
            	em.getTransaction().commit();
        	}
            System.out.println("Cantantes añadidos correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error!");
        }
	}
	public static Cantante buscarCantantePorID(long l) {
		
		Cantante p=null;
		try {
        	em.getTransaction().begin();
            p =em.find(Cantante.class,l);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } 
		return p;
	}
	public static Cantante buscarCantantePorNombre(String nombre) {
		List<Cantante> lista = null;
		try {
        	em.getTransaction().begin();
        	lista =em.createQuery("SELECT c FROM Cantante c where c.nombre= :nombre", Cantante.class)
        			.setParameter("nombre", nombre)
                    .getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } 
		return lista.getFirst();
	}
	public static void eliminarCantante(String nombre) {
		Cantante c=buscarCantantePorNombre(nombre);
		if (c!=null) {
			em.getTransaction().begin();
			em.remove(c);
			System.out.println(nombre+" eliminado correctamente");
			em.getTransaction().commit();
		}else System.out.println("Cantante no encontrado");
		
	}
	public static void añadirCantante (Cantante c) {
		em.getTransaction().begin();
		em.persist(c);
		System.out.println("Cantante añadido correctamente");
		em.getTransaction().commit();
	}
	
	public static void añadirAlbumaACantante(Cantante c, Album a) {
		c.addAlbum(a);
		em.getTransaction().begin();
		em.merge(c);
		//em.persist(c);
		em.getTransaction().commit();
	}

}
