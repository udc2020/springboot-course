# SpringBoot Course 🔥

## By UltrasDzCoder You Can see full course [here](https://www.youtube.com/playlist?list=PLGMjH0KDBZRgdAx0960fvAIIOP7EDnWru)

---

## Install SpringBoot :

1. You need Install java , InteliJ Idea
2. Go To *Spring initializer* [here](https://start.spring.io/)
3. add spring web , postgres driver.
4. Generate Prject & Download it .
5. Extract the project with winrar.
6. now open it with intliJ IDEA .

--- 

# First App :

1. Add annotation **@RestController** to MainClass .
2. Create new Method hello()

            @GetMapping
            public String  hello(){
               return "<h1>hello udc from spring boot</h1>";
            }

--- 

# Add Json Data :

1. replace String type to List (from java.util)
2. replace returning to List.of("html","css"....)
3. result :

              @GetMapping
              public List<String>  hello(){
                return List.of("hello", "udc" ,"from" ,"spring" ,"boot");
               }

--- 

# Create Users Model :

1. Create new package name _users_
2. Create into _users_ class file **Users**
3. initialize private propreties id:Long,name:String,phone:String
4. add empty contracture Alt+insert
5. add contracture for all props
6. add contracture without id
7. add setters & getters for all props
8. add to string contracture

              public class Users {
                  private Long id;
                     private String name;
                     private String phone;

                     public Users() {
                     }

                     public Users(Long id, String name, String phone) {
                     this.id = id;
                     this.name = name;
                     this.phone = phone;
                     }

                     public Users(String name, String phone) {
                     this.name = name;
                     this.phone = phone;
                     }

                     public Long getId() {
                     return id;
                     }

                     public void setId(Long id) {
                     this.id = id;
                     }

                     public String getName() {
                     return name;
                     }

                     public void setName(String name) {
                     this.name = name;
                     }

                     public String getPhone() {
                     return phone;
                     }

                     public void setPhone(String phone) {
                     this.phone = phone;
                     }

                     @Override
                     public String toString() {
                     return "Users{" +
                     "id=" + id +
                     ", name='" + name + '\'' +
                     ", phone='" + phone + '\'' +
                     '}';
                     }
                     }

9. Now replace string to _Users_ like that

                     @GetMapping
                        public List<Users> hello (){
                            return List.of(
                                  new Users(
                                            1L,
                                            "Cyrus Burt",
                                            "1-456-842-7528"
                                           ),
                                   new Users(
                                            2L,
                                            "Ethan Simpson",
                                            "(493) 778-2015"
                                             )
                                         );
                        }

--- 

# Create Controller :

1. Create new class File named **UsersController**
2. Remove @RestController from mainApp with entire hello()
3. past the hello in **UsersController**
4. Add to class Controller & request mapping

   @RestController @RequestMapping(path="api/users")
   public class UsersController {

   @GetMapping public List<Users> getUsers (){ return List.of(
   new Users(
   1L,
   "Cyrus Burt",
   "1-456-842-7528"
   ), new Users(
   2L,
   "Ethan Simpson",
   "(493) 778-2015"
   )
   ); }

--- 

# Connect To db :

1. in _Users_ add

       import javax.persistence.*;

       @Entity
       @Table

with :

      @Id
      @SequenceGenerator(
         name = "users_sequence",
         sequenceName = "users_sequence",
         allocationSize = 1
      )

      @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
      )

2. now install postgreSql
    1. add bin (postgresql) to path
    2. open git or cmd
    3. type **_psql -U postgres_**
    4. type password (0000)
    5. type **\! cls** to cleaning window
    6. type **\l** to show databases
    7. type **\c <databasename>** to redirect of this DB
    8. type \d to display (You can display with <name>)
    9. add

            CREATE DATABASE usersdata;

3. in resource/application.properties :

         spring.datasource.url=jdbc:postgresql://localhost:5432/usersdata
         spring.datasource.username=postgres
         spring.datasource.password=0000
         spring.jpa.hibernate.ddl-auto=create-drop spring.jpa.show-sql=true
         spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
         spring.jpa.properties.hibernate.format_sql=true

---

# Database Layer :

1. in users folder Create new interface _UsersRepository_
2. now write in UsersRepository

       @Repository
       public interface UsersRepository extends JpaRepository<Users,Long> {


       }

3. in UsersService :

       private final UsersRepository usersRepository;
       
       @Autowired
       public UsersService(UsersRepository usersRepository) {
       this.usersRepository = usersRepository;
       }
          
       public List<Users> getUsers (){
       return usersRepository.findAll();
       }

--- 

# Inserting Dommy data  :

1. Create UsersConfig Class
2. past this

@Configuration public class UsersConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository repository) {
        return args -> {
            Users Cyrus = new Users(

                    "Cyrus Burt",
                    "1-456-842-7528"
            );

            Users Ethan = new Users(

                    "Ethan Simpson",
                    "(493) 778-2015"
            );

            repository.saveAll(
                    List.of(Cyrus,
                            Ethan)
            );

        };
     }
    }

---

# Post in body  :

1. in _UsersController_ :

       @PostMapping
       public void registerUser( @RequestBody Users user){
       usersService.addUser(user);
       }

2. in _UsersService_ : 

       // POST: /api/users
       public void addUser(Users user) {
          System.out.println(user);
       }
3. send data in postman with row


--- 

# Delete Data  :
1. in _UsersController_ 
   
        @DeleteMapping(path= "{userId}")
        public void deleteUser(@PathVariable("userId") Long userId){
          usersService.removeUser(userId);
        }

2. in _UsersService_ 

       public void removeUser(Long userId) {

          boolean exists = usersRepository.existsById(userId);

          if (!exists) throw new IllegalStateException("Not Found");

           usersRepository.deleteById(userId);
       }

3. Now in postman /api/users/id


--- 

# Update Data  :

1. in _UsersController_ 

        @PutMapping(path = "{userId}")
        public void updateUser(
         @PathVariable("userId") Long userId,
         @RequestParam(required = false) String name,
         @RequestParam(required = false) String phone) {

            usersService.updateUser(userId,name,phone);
        }


2. in _UsersService_


      // PUT: /api/users
      @Transactional
      public void updateUser(Long userId, String name, String phone) {
      Users user = usersRepository.findById(userId).orElseThrow(
      () -> new IllegalStateException("user id :" + userId + "dont exists")
      );

        if (name != null
                && name.length() > 0 &&
                !Objects.equals(user.getName(),name)

        ) {
            user.setName(name);
        }

        if (phone != null
                && phone.length() > 0 &&
                !Objects.equals(user.getPhone(),phone)

        ) {
            user.setPhone(phone);
        }

    }
