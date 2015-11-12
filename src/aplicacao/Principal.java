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
			//1 - Mostrar a dura��o total de um dado �lbum
			case 1:
				em = emf.createEntityManager();//Instancia��o do EntityManager -- conex�o com o banco de dados
				System.out.println("Digite o c�digo do �lbum: ");
				cod = Integer.parseInt(sc.nextLine());
				alb = em.find(Album.class, cod);//busca o �lbum no banco de dados passado o cod como argumento
				if(alb == null){//Se o �lbum for nulo
					System.out.println("�lbum inexistente!");
				}else{
					System.out.println("Dura��o do �lbum " + alb.getNome() + ": " + alb.duracao());
				}
				break;
				
			//2 - Listar todos os �lbuns com seus artistas	
			case 2:
				em= emf.createEntityManager();//cria��o de conex�o com o banco de dados
				
				String s1 = "SELECT a FROM Album a";// String para consulta na base de dados usando o JPQL - linguagem de SQL no JPA
				Query q1 = em.createQuery(s1);//Um obejto de consulta do JPA
				//O "a" � o apelido obrigat�rio do banco de dados e o nome do banco - Album - tem de ser mai�sculo igual a classe de mesmo nome.
				@SuppressWarnings("unchecked")//Evitar erros sobre convers�o de tipos - java e SQL - no getResultList
				List<Album> listAlbuns = q1.getResultList();//Lista de resultados
				
				System.out.println("Listagem de �lbuns: ");
				for(Album x : listAlbuns){
					System.out.println(x.getNome() + ", " + x.getAno() + ", " + x.getArtista().getNome());
				}
				em.close();//Encerra o objeto respons�vel
				
				break;
			
			//3 - Cadastrar um novo �lbum	
			case 3:
				em = emf.createEntityManager();
				
				String s2 = "SELECT a FROM Artista a";
				Query q2 = em.createQuery(s2);
				@SuppressWarnings("unchecked")//Evitar erros sobre convers�o de tipos - java e SQL - no getResultList
				List<Artista> listArtistas = q2.getResultList();
				System.out.println("Artistas existentes: ");
				for(Artista x : listArtistas){
					System.out.println(x);
				}
				System.out.println("Digite o c�digo do artista do novo �lbum a ser inserido: ");
				int codArtista = Integer.parseInt(sc.nextLine());
				//Busca no banco de dados
				art = em.find(Artista.class, codArtista);
				//Se o c�digo for nulo
				if(art == null){
					System.out.println("C�digo de artista inexistente!");			
				}
				else{
					System.out.println("\nDigite o nome do novo �lbum: ");
					String nome = sc.nextLine();
					System.out.println("Digite o ano do novo �lbum: ");
					int ano = Integer.parseInt(sc.nextLine());
					alb = new Album(null, nome, ano, art);//Instancia��o do novo �lbum
					em.getTransaction().begin();//Inicia a transa��o
					try{
						em.persist(alb);//Comando do JPA para inser��o
						em.getTransaction().commit();//Confirmar
						System.out.println("O �lbum foi inserido. C�digo: " + alb.getCodAlbum());
					}
					catch(Exception e){//Se der algum problema
						if(em.getTransaction().isActive()){
							em.getTransaction().rollback();
						}
						System.out.println("Falha ao inserir �lbum!");
					}
				}
				em.close();
				break;
			
			//Sair do programa
			case 4:
				System.out.println("Fim do programa!");
				break;
			
			default:
				System.out.println("Op��o inv�lida!");
			}
		} while(op != 4);
		
		emf.close();
		sc.close();
	}

}
