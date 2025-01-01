package uz.pdp.telegram_quiz.db;

import lombok.Getter;
import org.springframework.stereotype.Service;
import uz.pdp.telegram_quiz.entity.Question;

import java.util.List;

@Service
public interface QuizBot {
//    List<Question> questions = List.of(
//            new Question("""
//                Tovar-pul munosabatlari nima?
//                """,
//                    List.of(
//                            "Tovar ishlab chiqarish va tovarlarni pulga ayirboshlash munosabatlari",
//                            "Tovar ishlab chiqarish va tovarlarni o’zaro barter usulida ayirboshlash",
//                            "Natural ishlab chiqarish",
//                            "Pul orqali iqtisodiyotni boshqarish"),
//                    0),
//            new Question("""
//                Qadimgi davrda odamlar o‘z ehtiyojidan ortib qolgan iste’mol mahsulotlari turib qolmasligi va saqlashning imkoniyati bo‘lmaganligi bois qanday yo’l tutishgan?
//                """,
//                    List.of(
//                            "O‘zlariga yaqin yoki tanish odamlarga bergan",
//                            "Saqlashda davom etishgan",
//                            "Berkitib qo’yishgan",
//                            "Bankka topshirib qo’yishgan"),
//                    0),
//            new Question("""
//                Dastlabki metal tangalar qayerda zarb qilingan?
//                """,
//                    List.of(
//                            "Xitoy va Lidiya",
//                            "Misr va Xitoy",
//                            "Misr va Qadimgi Yunoniston",
//                            "Lidiya va Misr"),
//                    0),
//            new Question("""
//                Oltinning pul sifatida muomalada amal qilishi qaysi davrlarda to'xtatildi?
//                """,
//                    List.of(
//                            "XX asrning 30-70 -yillarida",
//                            "XX asrning 50-60-yillarida",
//                            "XIX asrning 90-yillarida",
//                            "XIX asrning 30-70-yillarida"),
//                    0),
//            new Question("""
//                Oltin pul sifatida muomaladan chiqarilgandan so'ng uning o'rnini qanday pullar egalladi?
//                """,
//                    List.of(
//                            "Qog'oz va kredit",
//                            "Qog'oz va kumush",
//                            "Qog'oz, kredit, kumush",
//                            "Kumush va mis"),
//                    0),
//            new Question("""
//                Dastlabki qog'oz pullar qayerda paydo bo'lgan?
//                """,
//                    List.of(
//                            "Xitoy",
//                            "Misr",
//                            "Mesopotamia",
//                            "Yunoniston"),
//                    0),
//            new Question("""
//                Pul nazariyasi o’z ichiga qanday masalalarni oladi?
//                """,
//                    List.of(
//                            "Pulning mohiyati, zaruriyati, funksiyalari va uning iqtisodiyotga ta’siri",
//                            "Pulga bo’lgan talab va taklif",
//                            "Pul massasi va pul muomalasi qonunlari",
//                            "Monetar siyosat"),
//                    0),
//            new Question("""
//                Pulning funksiyalari tog'ri berilgan qatorni aniqlang.
//                """,
//                    List.of(
//                            "Qiymat o’lchov, muomala vositasi, jamg'arish vositasi",
//                            "Ayirboshlash, jamg'arish, nazorat",
//                            "Ayirboshlash, jamg'arish, tartibga solish",
//                            "Ayirboshlash, tartibga solish, rag'batlantirish"),
//                    0),
//            new Question("""
//                “Pulning mohiyati ijtimoiy zaruriy mehnatning rivojlanishi bilan belgilanmasdan oltin va kumushning tabiiy xususiyatlari bilan belgilanadi” ushbu fikr qaysi nazariyaga oid?
//                """,
//                    List.of(
//                            "Nominallik nazariyasi",
//                            "Miqdoriylik nazariyasi",
//                            "Metallik nazariyasi",
//                            "Reallik nazariyasi"),
//                    2),
//            new Question("""
//                “Pul - bu muomalaning buyuk g’ildiragi, savdo-sotiqning yuksak quroli” ushbu fikr kim tomonidan aytilgan?
//                """,
//                    List.of(
//                            "D.Yum",
//                            "A.Smit",
//                            "A. Marshall",
//                            "M.Fridmen"),
//                    1),
//            new Question("""
//                Pulning miqdoriy nazariyasiga ko’ra…
//                """,
//                    List.of(
//                            "Pulning qiymati uning ko‘rsatilgan nominali bilan aniqlanadi.",
//                            "Pulning sotib olish qobiliyati xuddi baho kabi bozorda aniqlanadi",
//                            "Pulning mohiyati ijtimoiy zaruriy mehnatning rivojlanishi bilan belgilanadi",
//                            "Pulning qiymatini davlat belgilaydi"),
//                    1),
//            new Question("""
//                Pulning muomala vositasi funksiyasi qachon namoyon bo’ladi?
//                """,
//                    List.of(
//                            "Asosan tovarlar qiymatini baho shaklida aks ettirish orqali namoyon bo’ladi",
//                            "To’lov kechikkan holatlarning barchasida",
//                            "Oldindan ko’rsatilgan xizmatlar va sotib olingan tovarlar uchun to’langanda",
//                            "Tovarlarni sotib olish va xizmatlarni ko’rsatish uchun to’lov bir vaqtda amalga oshirilganda"),
//                    3),
//            new Question("""
//                Pulning to’lov vositasi funksiyasi qachon namoyon bo’ladi?
//                """,
//                    List.of(
//                            "Asosan tovarlar qiymatini baho shaklida aks ettirish orqali namoyon bo’ladi",
//                            "To’lov kechikkan holatlarning barchasida",
//                            "Tovarlarni sotib olish va xizmatlarni ko’rsatish uchun to’lov bir vaqtda amalga oshirilganda",
//                            "Puldan foydalanmasdan kishilar o’zi bilan olib yurganda"),
//                    1),
//            new Question("""
//                Vaqtincha foydalanilmayotgan pullar qanday vazifani bajardi?
//                """,
//                    List.of(
//                            "To’lov vositasi funksiyasini",
//                            "Muomala vositasi funksiyasini",
//                            "Jamg’arma vositasi funksiyasini",
//                            "Qiymat o’lchov funksiyasini"),
//                    2),
//            new Question("""
//                Bozor iqtisodiyoti sharoitida tovar va mahsulotlarning qiymati qanday aniqlanadi?
//                """,
//                    List.of(
//                            "Tovar va mahsulotning tannarxi asosida",
//                            "Ishlab chiqaruvchining foyda miqdori asosida",
//                            "Bozordagi talab va taklif asosida",
//                            "To’lanadigan soliq stavkalari asosida"),
//                    2),
//            new Question("""
//                Yevropa maktabi pulning nechta funksiyasini e’tirof etadi?
//                """,
//                    List.of(
//                            "2 ta",
//                            "3 ta",
//                            "4 ta",
//                            "5 ta"),
//                    3),
//            // Dastlabki 17 ta savolni oldin berilgan javobdan olish mumkin
//            new Question("""
//                Amerika ilmiy maktabi pulning qaysi funksiyalarini tan oladi?
//                """,
//                    List.of(
//                            "To’lov vositasi, jahon puli, jamg’arma vositasi",
//                            "Hisob, ayirboshlash vositasi, jamg’arish vositasi",
//                            "Qiymat-o’lchov, muomala vositasi, to’lov vositasi",
//                            "Jamg’arish vositasi, to’lov vositasi, muomala vositasi"),
//                    1),
//            new Question("""
//                “Pul bu – savdo – sotiqning g‘ildiragi emas, balki u yog‘, shu savdo – sotiq g‘ildiragini erkin va yumshoq yurishiga imkoniyat yaratadigan vositadir” ushbu fikr kimga tegishli?
//                """,
//                    List.of(
//                            "D.Yum",
//                            "A.Smit",
//                            "A. Marshall",
//                            "M.Fridmen"),
//                    0),
//            new Question("""
//                Miqdoriylik nazariyasining asoschisi kim?
//                """,
//                    List.of(
//                            "I.Fisher",
//                            "J.Boden",
//                            "T.Men",
//                            "J.Styuart"),
//                    1),
//            new Question("""
//                “Baholarning o’zgarishi muomaladagi pul miqdori bilan aniqlanadi” ushbu fikr qaysi nazariya vakillariga tegishli?
//                """,
//                    List.of(
//                            "Monetarism nazariyasi",
//                            "Miqdoriylik nazariyasi",
//                            "Metallik nazariyasi",
//                            "Nomenallik nazariyasi"),
//                    1),
//            new Question("""
//                Ilk bor vujudga kelgan pul nazariyasi qaysi?
//                """,
//                    List.of(
//                            "Metallik",
//                            "Nomenallik",
//                            "Miqdoriylik",
//                            "Reallik"),
//                    0),
//            new Question("""
//                “Tovar-pul yoki pul-tovar operatsiyasi bir vaqtning o’zida yuz beradi, ya’ni tovarlar nasiyaga sotilmaydi” ushbu jumla pulning qaysi funksiyasiga to’gri keladi?
//                """,
//                    List.of(
//                            "Jamg’arish",
//                            "To’lov",
//                            "Muomala vositasi",
//                            "Jahon puli"),
//                    2),
//            new Question("""
//                “Barcha tovarlar va xizmatlar qiymati umumiy ekvivalent bo’lgan pulda o’lchanadi” ushbu jumla pulning qaysi funksiyasiga to’gri keladi?
//                """,
//                    List.of(
//                            "Jamg’arish",
//                            "To’lov",
//                            "Muomala vositasi",
//                            "Qiymat o’lchovi"),
//                    3),
//            new Question("""
//                Emission bank bu…
//                """,
//                    List.of(
//                            "Tijorat banki",
//                            "Investitsion bank",
//                            "Markaziy bank",
//                            "Davlat banki"),
//                    2),
//            new Question("""
//                Pulning nazariyasi iqtisodiy kategoriya sifatida nechta guruhga ajratib o‘rganiladi?
//                """,
//                    List.of(
//                            "3 ta guruhga",
//                            "2 ta guruhga",
//                            "4 ta guruhga",
//                            "6 ta guruhga"),
//                    0),
//            new Question("""
//                Pulning nominal nazariyasi davrini shakllanishiga qanday omillar ta'sir qilgan?
//                """,
//                    List.of(
//                            "Muomalaga haqiqiy bo‘lmagan metall pullar kiritilganda",
//                            "Muomalaga qog‘oz pullar kiritilganda",
//                            "Muomalaga kumush va oltin tangalar kiritilganda",
//                            "Qog'oz pullarning bosilishi kengayganda"),
//                    0),
//            new Question("""
//                Quyidagilarning qaysi biri naqd pulsiz hisob-kitob shakllari hisoblanmaydi?
//                """,
//                    List.of(
//                            "E’lonnoma",
//                            "Inkasso topshiriqnomasi",
//                            "To’lov topshiriqnomasi",
//                            "To’lov talabnomasi"),
//                    0),
//            new Question("""
//                Hisob-kitob xujjatlari ichida to’lovlar tez amalga oshiriladigan va ko’p qo’llaniladigan hujjatni kursating.
//                """,
//                    List.of(
//                            "To’lov topshiriqnomasi",
//                            "To’lov talabnomasi",
//                            "Akkreditiv",
//                            "Inkasso, kredit kartochkalari"),
//                    0),
//            new Question("""
//                Naqd pulsiz aylanmaning qatnashuvchilari kimlar hisoblanadi?
//                """,
//                    List.of(
//                            "Aholi, xo’jalik subyektlari, davlat va bank-moliya muassasalari",
//                            "Xo’jalik subyektlari",
//                            "Aholi",
//                            "Davlat"),
//                    0),
//            new Question("""
//                Plastik kartalarning qulayligi qaysi javobda noto’g’ri berilgan?
//                """,
//                    List.of(
//                            "Cheklanmagan muddatda foydalanish mumkin",
//                            "Olib yurishda va foydalanishda qulay",
//                            "Yo’qotib qo’ysangiz ham pulingiz saqlanib qoladi",
//                            "Banklarning xarajat qiladigan mablag’lari tejaladi"),
//                    0),
//            new Question("""
//                Muomaladan ortiqcha pullarni qaytarib olishni qanday bank amalga oshiradi?
//                """,
//                    List.of(
//                            "Markaziy bank",
//                            "Tijorat banki",
//                            "Moliya Vazirligi",
//                            "Xalq banki"),
//                    0),
//            new Question("""
//                Pul massasi bu:
//                """,
//                    List.of(
//                            "Muomaladagi naqd va naqd pulsiz aylanmadagi pullarning yig’indisi",
//                            "Muomaladagi naqd pullar yig’indisi",
//                            "Muomaladagi naqdsiz pullar yig’indisi",
//                            "Muomaladagi tanga pullarning yig’indisi"),
//                    0),
//            new Question("""
//                To’lov topshiriqnomasi bu:
//                """,
//                    List.of(
//                            "```Sotib oluvchining bankka o’z hisobvarag’idan mablag’ni sotuvchi hisobiga o’tkazib berish haqidagi yozma topshirig’i```",
//                            "```Sotuvchi jo’natilgan tovarlar va ko’rsatilgan xizmatlar uchun mablag’ni talab qilishi haqidagi hujjat```",
//                            "```Bankning sotuvchiga to’lovni amalga oshirish to’g’risidagi yozma topshirig’i```",
//                            "```Sotuvchi va sotib oluvchi o’rtasidagi shartnomaga ko’ra to’lovni amalga oshirish bo’yicha mablag’ oluvchining yozma topshirig’i```"),
//                    0),
//            new Question("""
//                To’lov talabnoma bu:
//                """,
//                    List.of(
//                            "Sotuvchi jo’natilgan tovarlar va ko’rsatilgan xizmatlar uchun mablag’ni talab qilishi haqidagi hujjat",
//                            "Sotib oluvchining bankka o’z hisobvarag’idan mablag’ni sotuvchi hisobiga o’tkazib berish haqidagi yozma topshirig’i",
//                            "Bankning sotuvchiga to’lovni amalga oshirish to’g’risidagi yozma topshirig’i",
//                            "Sotuvchi va sotib oluvchi o’rtasidagi shartnomaga ko’ra to’lovni amalga oshirish bo’yicha mablag’ oluvchining yozma topshirig’i"),
//                    0),
//            new Question("""
//                Akkreditivning shakllari qanday javobda to’g’ri keltirilgan?
//                """,
//                    List.of(
//                            "Qoplangan va qoplanmagan akkreditiv",
//                            "Kreditlanadigan va kreditlanmaydigan",
//                            "To’lanadigan va to’lanmaydigan",
//                            "Daromadli va daromadsiz"),
//                    0),
//            new Question("""
//                Aktseptli to’lov talabnomalarda:
//                """,
//                    List.of(
//                            "Bank dastlab belgilangan muddatda mijozning roziligini oladi, so’ng to’lovni amalga oshiradi",
//                            "Bank dastlab to’lovni amalga oshiradi so’ng mijozning roziligini oladi",
//                            "Bank belgilangan muddatda to’lovni amalga oshirib bu haqda mijozga xabar beradi",
//                            "Bank hujjat kelib tushgan zahoti to’lovni amalga oshiradi va bu haqda mijozga xabar beradi"),
//                    0),
//            new Question("""
//                Aktseptsiz to’lov talabnomalarda:
//                """,
//                    List.of(
//                            "Bank hujjat kelib tushgan zahoti to’lovni amalga oshiradi va bu haqda mijozga xabar beradi",
//                            "Bank dastlab to’lovni amalga oshiradi so’ng mijozning roziligini oladi",
//                            "Bank dastlab belgilangan muddatda mijozning roziligini oladi, so’ng to’lovni amalga oshiradi",
//                            "Bank belgilangan muddatda to’lovni amalga oshirib, bu haqda mijozga xabar beradi"),
//                    0),
//            new Question("""
//                Inkasso topshiriqnomasi:
//                """,
//                    List.of(
//                            "```Bankka to’lovchining hisobvarag’idan so’zsiz tartibda mablag’larni hisobdan chiqarish huquqini beradi```",
//                            "```Mijozning hisobvarag’iga uning roziligsiz mablag’larni kirim qilish huquqini beradi```",
//                            "```Mijozning o’z hisobvarag’idan mablag’ni so’zsiz ko’chirish haqidagi topshirig’i hisoblanadi```",
//                            "```Mablag’ to’lovchi o’z hisobidan to’lovlarni amalga oshirish bo’yicha topshirig’i```"),
//                    0)
//            // Shu tartibda davom ettirish mumkin
//    );
}
