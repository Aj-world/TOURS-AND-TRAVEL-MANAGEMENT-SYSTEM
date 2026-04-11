@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public Payment createPayment(Long bookingId){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();

        payment.setBookingId(bookingId);
        payment.setAmount(booking.getTotalPrice());
        payment.setStatus(PaymentStatus.CREATED);

        return paymentRepository.save(payment);
    }

    public void confirmPayment(Long bookingId){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
    }
}