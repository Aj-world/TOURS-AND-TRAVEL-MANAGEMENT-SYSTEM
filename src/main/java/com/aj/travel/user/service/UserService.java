@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}