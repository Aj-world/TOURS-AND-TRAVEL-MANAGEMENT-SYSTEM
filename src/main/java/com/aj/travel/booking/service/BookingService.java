@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TravelPackageRepository packageRepository;

    public Booking createBooking(Long userId, Long packageId){

        TravelPackage travelPackage =
                packageRepository.findById(packageId)
                        .orElseThrow(() -> new RuntimeException("Package not found"));

        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setPackageId(packageId);
        booking.setTotalPrice(travelPackage.getPrice());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(Long userId){
        return bookingRepository.findByUserId(userId);
    }
}