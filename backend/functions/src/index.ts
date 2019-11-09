import * as functions from 'firebase-functions';

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

const json = 
[
    {
      "_id": "5dad9caf0cb4cc0c771c0f9b",
      "picture": "http://severnbridgevending.co.uk/images/snack&drink.jpg",
      "good": "Snacks",
      "name": "Schneider",
      "company": "KONNECT",
      "address": "825 Amherst Street, Gilgo, Virginia, 228",
      "latitude": "-39.521648",
      "longitude": "-96.56682"
    },
    {
      "_id": "5dad9cafe89930fe67b25a38",
      "picture": "https://5.imimg.com/data5/KW/RS/MY-12937345/cosmetics-vending-machine-500x500.jpg",
      "good": "Snacks",
      "name": "Garrison",
      "company": "SPHERIX",
      "address": "545 Moore Street, Hollins, Minnesota, 2236",
      "latitude": "-16.826068",
      "longitude": "-101.530495"
    },
    {
      "_id": "5dad9caff65921ef4d6f052d",
      "picture": "https://images-na.ssl-images-amazon.com/images/I/61kg9T45RHL._SY550_.jpg",
      "good": "Sweets",
      "name": "Haynes",
      "company": "SNORUS",
      "address": "316 Schenck Court, Diaperville, Northern Mariana Islands, 6846",
      "latitude": "-50.294109",
      "longitude": "-31.331587"
    },
    {
      "_id": "5dad9cafd5147c9e469c93df",
      "picture": "https://www.candymachines.com/GetImage.ashx?Path=%7E%2Fimages%2Fbulk_vending_machines%2Ftriple-time-vending-machine-red.jpg&maintainAspectRatio=true",
      "good": "Sweets",
      "name": "Ross",
      "company": "VIDTO",
      "address": "920 Bassett Avenue, Tryon, Ohio, 9609",
      "latitude": "-47.472334",
      "longitude": "64.961173"
    },
    {
      "_id": "5dad9caf15a2122c249c7fbd",
      "picture": "https://5.imimg.com/data5/OD/NA/MY-12937345/water-bottle-vending-machine-500x500.jpg",
      "good": "Water",
      "name": "Ballard",
      "company": "ZENTHALL",
      "address": "130 Navy Walk, Grimsley, Wisconsin, 8022",
      "latitude": "-81.54813",
      "longitude": "108.419676"
    },
    {
      "_id": "5db0807aefda6498730a94ba",
      "picture": "https://sc02.alicdn.com/kf/HTB1KYgxN4TpK1RjSZR0q6zEwXXaL/buy-japanese-automatic-vegetable-lift-vending-machines.jpg_300x300.jpg",
      "good": "Snacks",
      "name": "Green",
      "company": "SOFTMICRO",
      "address": "322 McDonald Avenue, Dundee, Colorado, 5072",
      "latitude": "71.184591",
      "longitude": "-56.688294"
    },
    {
      "_id": "5db0807ad1b429ee99f5723a",
      "picture": "https://www.gumballs.com/media/extendware/ewimageopt/media/inline/e1/9/2-triple-pod-gumball-candy-machine-combo-rack-a27.jpg",
      "good": "Sweets",
      "name": "Alexandria",
      "company": "XYMONK",
      "address": "337 Crosby Avenue, Kennedyville, Oklahoma, 2062",
      "latitude": "12.087055",
      "longitude": "91.761315"
    },
    {
      "_id": "5db0807b430e824c74b17ed5",
      "picture": "https://www.amequipmentsales.com/wp-content/uploads/2016/08/AMS-OUTSIDER_SNACK-opt.jpg",
      "good": "Snacks",
      "name": "Yesenia",
      "company": "SENTIA",
      "address": "709 Schenck Avenue, Worcester, New Jersey, 9986",
      "latitude": "-45.196874",
      "longitude": "-173.856252"
    },
    {
      "_id": "5db0807bfe64f0bb82739be1",
      "picture": "https://www.professionalvendingsupply.com/assets/images/product%20images/candy%20vending%20machines/table%20top%20machines/largeballbubble.jpg",
      "good": "Sweets",
      "name": "Lea",
      "company": "ACIUM",
      "address": "491 Seagate Terrace, Guthrie, Maryland, 1177",
      "latitude": "16.791627",
      "longitude": "161.136412"
    },
    {
      "_id": "5db0807b02f99821a5602ee4",
      "picture": "https://www.hitechro.net/image/cache/catalog/products/Water%20ATM-400x400.png",
      "good": "Water",
      "name": "Benson",
      "company": "UTARIAN",
      "address": "742 Gold Street, Bergoo, Louisiana, 2587",
      "latitude": "36.640635",
      "longitude": "161.544492"
    }
  ]
    ;

export const vendings = functions.https.onRequest((request, response) => {
 response.send(json);
});
