package aplicacao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dominio.Album;
import dominio.Artista;

public class Principal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("meujpa");
		EntityManager em = null;
		int op, cod;
		Album alb = null;
		Artista art = null;
		
		do{
			op = Tela.menu(sc);
			
			switch(op){
			//1 - Mostrar a duração total de um dado álbum
			case 1:
				em = emf.createEntityManager();//Instanciação do EntityManager -- conexão com o banco de dados
				System.out.println("Digite o código do álbum: ");
				cod = Integer.parseInt(sc.nextLine());
				alb = em.find(Album.class, cod);//busca o álbum no banco de dados passado o cod como argumento
				if(alb == null){//Se o álbum for nulo
					System.out.println("Álbum inexistente!");
				}else{
					System.out.println("Duração do álbum " + alb.getNome() + ": " + alb.duracao());
				}
				break;
				
			//2 - Listar todos os álbuns com seus artistas	
			case 2:
				em= emf.createEntityManager();//criação de conexão com o banco de dados
				
				String s1 = "SELECT a FROM Album a";// String para consulta na base de dados usando o JPQL - linguagem de SQL no JPA
				Query q1 = em.createQuery(s1);//Um obejto de consulta do JPA
				//O "a" é o apelido obrigatório do banco de dados e o nome do banco - Album - tem de ser maiúsculo igual a classe de mesmo nome.
				@SuppressWarnings("unchecked")//Evitar erros sobre conversão de tipos - java e SQL - no getResultList
				List<Album> listAlbuns = q1.getResultList();//Lista de resultados
				
				System.out.println("Listagem de álbuns: ");
				for(Album x : listAlbuns){
					System.out.println(x.getNome() + ", " + x.getAno() + ", " + x.getArtista().getNome());
				}
				em.close();//Encerra o objeto responsável
				
				break;
			
			//3 - Cadastrar um novo álbum	
			case 3:
				em = emf.createEntityManager();
				
				String s2 = "SELECT a FROM Artista a";
				Query q2 = em.createQuery(s2);
				@SuppressWarnings("unchecked")//Evitar erros sobre conversão de tipos - java e SQL - no getResultList
				List<Artista> listArtistas = q2.getResultList();
				System.out.println("Artistas existentes: ");
				for(Artista x : listArtistas){
					System.out.println(x);
				}
				System.out.println("Digite o código do artista do novo álbum a ser inserido: ");
				int codArtista = Integer.parseInt(sc.nextLine());
				//Busca no banco de dados
				art = em.find(Artista.class, codArtista);
				//Se o código for nulo
				if(art == null){
					System.out.println("Código de artista inexistente!");			
				}
				else{
					System.out.println("\nDigite o nome do novo álbum: ");
					String nome = sc.nextLine();
					System.out.println("Digite o ano do novo álbum: ");
					int ano = Integer.parseInt(sc.nextLine());
					alb = new Album(null, nome, ano, art);//Instanciação do novo álbum
					em.getTransaction().begin();//Inicia a transação
					try{
						em.persist(alb);//Comando do JPA para inserção
						em.getTransaction().commit();//Confirmar
						System.out.println("O álbum foi inserido. Código: " + alb.getCodAlbum());
					}
					catch(Exception e){//Se der algum problema
						if(em.getTransaction().isActive()){
							em.getTransaction().rollback();
						}
						System.out.println("Falha ao inserir álbum!");
					}
				}
				em.close();
				break;
			
			//Sair do programa
			case 4:
				System.out.println("Fim do programa!");
				break;
			
			default:
				System.out.println("Opção inválida!");
			}
		} while(op != 4);
		
		emf.close();
		sc.close();
	}

}
