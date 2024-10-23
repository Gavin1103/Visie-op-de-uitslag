export const formatNumber = (number: number): string => {
    return new Intl.NumberFormat('nl-NL').format(number);
};
