import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Genero {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	String nombre;
	/*@ManyToMany
	@JoinTable(
			name="album_genero",
			joinColumns=@JoinColumn(name="album_id"),
			joinColumns=@JoinColumn(name="genero_id"))*/
	
	
	
	public Genero() {}
	
	public Genero(String nombre) {
		this.nombre=nombre;
	}

}
