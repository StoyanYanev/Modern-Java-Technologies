package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
    private static final int DAYS_BEFORE = 30;

    private Map<String, Set<Offer>> productOffers;

    public ShoppingDirectoryImpl() {
        productOffers = new HashMap<>();
    }

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        validateProduct(productName);

        return getAllOffersInLastMonth(productName);
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        validateProduct(productName);

        Set<Offer> allOffersForProduct = getAllOffersInLastMonth(productName);
        if (allOffersForProduct.isEmpty()) {
            throw new NoOfferFoundException();
        }

        return allOffersForProduct.iterator().next();
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        validateProduct(productName);

        return getProductStatistics(productName);
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        validateOffer(offer);

        Set<Offer> allProductOffers = productOffers.get(offer.getProductName());
        if (allProductOffers == null) {
            allProductOffers = new TreeSet<>(new PriceOfferComparator());
        }

        if (allProductOffers.contains(offer)) {
            throw new OfferAlreadySubmittedException();
        }

        allProductOffers.add(offer);
        productOffers.put(offer.getProductName(), allProductOffers);
    }

    private void validateProduct(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        }
        if (!productOffers.containsKey(productName)) {
            throw new ProductNotFoundException();
        }
    }

    private void validateOffer(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException();
        }
    }

    private Set<Offer> getAllOffersInLastMonth(String productName) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(DAYS_BEFORE);
        Set<Offer> allOffersInLastMonth = new TreeSet<>(new PriceOfferComparator());

        Set<Offer> offers = productOffers.get(productName);
        for (Offer currentOffer : offers) {
            if (currentOffer.getDate().compareTo(startDate) > 0
                    && currentOffer.getDate().compareTo(currentDate) <= 0) {
                allOffersInLastMonth.add(currentOffer);
            }
        }

        return allOffersInLastMonth;
    }

    private Set<PriceStatistic> getProductStatistics(String productName) {
        Map<LocalDate, Set<Offer>> productStatisticsByDays = buildProductStatisticsByDays(productName);

        Set<PriceStatistic> productStatistics = new TreeSet<>(new DateOfferComparator());
        double lowerPrice;
        double averageTotalPrice;
        PriceStatistic statistic;
        for (Map.Entry<LocalDate, Set<Offer>> currentStatistic : productStatisticsByDays.entrySet()) {
            lowerPrice = currentStatistic.getValue().iterator().next().getTotalPrice();
            averageTotalPrice = getAverageTotalPriceByDate(currentStatistic.getValue());
            statistic = new PriceStatistic(currentStatistic.getKey(), lowerPrice, averageTotalPrice);
            productStatistics.add(statistic);
        }

        return productStatistics;
    }

    private Map<LocalDate, Set<Offer>> buildProductStatisticsByDays(String productName) {
        Map<LocalDate, Set<Offer>> statisticsByDays = new HashMap<>();

        Set<Offer> allOffersForProduct = productOffers.get(productName);
        Set<Offer> allOffersByDate;
        for (Offer currentOffer : allOffersForProduct) {
            allOffersByDate = statisticsByDays.get(currentOffer.getDate());
            if (allOffersByDate == null) {
                allOffersByDate = new TreeSet<>(new PriceOfferComparator());
            }
            allOffersByDate.add(currentOffer);
            statisticsByDays.put(currentOffer.getDate(), allOffersByDate);
        }

        return statisticsByDays;
    }

    private double getAverageTotalPriceByDate(Set<Offer> offersByDate) {
        if (offersByDate.isEmpty()) {
            return 0.0;
        }
        double sumOfTotalPrices = 0.0;
        for (Offer currentOffer : offersByDate) {
            sumOfTotalPrices += currentOffer.getTotalPrice();
        }

        return sumOfTotalPrices / offersByDate.size();
    }
}