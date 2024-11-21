// Helper function to get the color based on party name
function getPartyColor(partyName) {
    const partyMap = {
        'VVD': 'VVD',
        'CDA': 'CDA',
        'D66': 'D66',
        'PVV (Partij voor de Vrijheid)': 'PVV',
        'GROENLINKS / Partij van de Arbeid (PvdA)': 'PvdA',
        'Partij voor de Dieren': 'PartijvoordeDieren',
        'Volt': 'Volt',
        'BIJ1': 'BIJ1',
        'DENK': 'DENK',
        'Nieuw Sociaal Contract': 'NieuwSociaalContract',
        'Forum voor Democratie': 'FVD',
        'SP (Socialistische Partij)': 'SP',
        'BBB': 'BBB',
        'Piratenpartij - De Groenen': 'Piratenpartij',
        'ChristenUnie': 'ChristenUnie',
        'Staatkundig Gereformeerde Partij (SGP)': 'SGP',
        'LEF - Voor de Nieuwe Generatie': 'LEF',
        'VNL': 'VNL',
        '50PLUS': 'VijftigPLUS',
        'BVNL / Groep Van Haga': 'VNL',
        'Nederland met een PLAN': 'PLAN',
        'LP (Libertaire Partij)': 'LP',
        'Splinter': 'Splinter',
        'Partij voor de Sport': 'PartijvdSport',
        'Politieke Partij voor Basisinkomen': 'PolitiekePartijBasisinkomen',
        'Samen voor Nederland': 'SamenVoorNederland'
    };

    // Normalize the party name using the mapping
    const partyKey = partyMap[partyName] || partyName; // Fallback to the original name if not found

    // Define color mapping with distinct colors for each party
    const colors = {
        VVD: '#FF9900',                // People's Party for Freedom and Democracy
        CDA: '#00A651',                // Christian Democratic Appeal
        D66: '#009688',                // Democrats 66
        PVV: '#1D4E89',                // Party for Freedom
        PvdA: '#EB1C24',               // Labour Party
        SP: '#EF3340',                 // Socialist Party
        ChristenUnie: '#003399',       // Christian Union
        SGP: '#FF6600',                // Reformed Political Party
        PartijvoordeDieren: '#66CC33', // Party for the Animals
        Volt: '#68217A',               // Volt Netherlands
        JA21: '#FFD700',               // Right-wing political party JA21
        FVD: '#800000',                // Forum for Democracy
        DENK: '#00ADEF',               // DENK
        BBB: '#00A550',                // Farmer–Citizen Movement (BoerBurgerBeweging)
        BIJ1: '#FF0099',               // BIJ1
        VijftigPLUS: '#6A0DAD',        // 50PLUS Party
        Piratenpartij: '#5B5BE2',      // Pirate Party (Blue)
        LP: '#FFD700',                 // Libertarian Party (Gold)
        NLBeter: '#009999',            // NLBeter (Aqua)
        Splinter: '#FF4500',           // Splinter Party (Orange-Red)
        JezusLeeft: '#FFB6C1',         // Jesus Lives Party (Light Pink)
        CodeOranje: '#FFA500',         // Code Orange
        VNL: '#00008B',                // For the Netherlands (Dark Blue)
        NieuwSociaalContract: '#8A2BE2', // New Social Contract (Purple)
        PLAN: '#00CED1',                // Nederland met een PLAN (Turquoise)
        PolitiekePartijBasisinkomen: '#8B4513', // Political Party for Basic Income (Saddlebrown)
        PartijvdSport: '#A52A2A',        // Party for Sports (Brown)
        SamenVoorNederland: '#20B2AA',   // Together for Netherlands (Light Sea Green)
        LEF: '#8A2BE2',               // LEF - For the New Generation (Purple)
    };

    // Return the color corresponding to the party, or a default if not found
    return colors[partyKey] || '#FFFFFF'; // Default to white if color is not found
}

// Export the function
export default getPartyColor;
