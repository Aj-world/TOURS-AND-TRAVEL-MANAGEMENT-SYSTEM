@Service
@RequiredArgsConstructor
@Transactional
public class PackageService {

    private final TravelPackageRepository packageRepository;

    public TravelPackage createPackage(TravelPackage travelPackage){
        return packageRepository.save(travelPackage);
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getActivePackages(){
        return packageRepository.findByStatus(PackageStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public TravelPackage getPackage(Long id){
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}