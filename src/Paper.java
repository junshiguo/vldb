import java.awt.Stroke;
import java.util.ArrayList;

public class Paper
{
    public int id;

    public String link;

    public String title;

    public String authors;

    public String paperAbstract;

    public ArrayList<String> photos;

    public ArrayList<String> bios;

    public String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }    
    
    public Paper(int id, String link, String title, String authors, String paperAbstract,
            ArrayList<String> photos, ArrayList<String> bios)
    {
        this.id = id;
        this.link = stripNonValidXMLCharacters(link);
        this.title = stripNonValidXMLCharacters(title);
        this.paperAbstract = stripNonValidXMLCharacters(paperAbstract).replaceAll("<|>", "");
        this.photos = photos;
        this.bios = bios;
        this.authors = stripNonValidXMLCharacters(authors);
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append(id + ". " + title + "\nby " + authors + "\n");
        if (link != null && !link.isEmpty())
            buf.append("Downloadable at: " + link + "\n");
        if (paperAbstract != null && !paperAbstract.isEmpty())
            buf.append("Abstract:\n" + paperAbstract);
        /*
         * for (int i = 0, imax = authorData.length; i < imax; i++) {
         * buf.append("\t\t\t<authorInfo>\n"); if (authorData[i][PHOTO] != null
         * && !authorData[i][PHOTO].isEmpty()) buf.append("\t\t\t\t<photo>" +
         * authorData[i][PHOTO] + "</photo>\n"); if (authorData[i][BIO] != null
         * && !authorData[i][BIO].isEmpty()) buf.append("\t\t\t\t<bio>" +
         * authorData[i][BIO] + "</bio>\n");
         * buf.append("\t\t\t</authorInfo>\n"); }
         */
        if (photos.size() != 0)
            buf.append("+ author photos and bios\n");
        return buf.toString();
    }
}
