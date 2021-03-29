package Challenge3;

import java.util.Arrays;
import java.util.Collection;

public class Solution {
    static class Version implements Comparable {
        Integer major = null, minor = null, revision = null;

        @Override
        public int compareTo(Object o) {
            Version v = null;
            if (o instanceof Version) {
                v = (Version) o;
            }
            if (this.major > v.major)
                return 1;
            else if (this.major < v.major)
                return -1;
            else {
                int minor_this, minor_v;
                if (this.minor == null && v.minor != null)
                    return -1;
                else if (this.minor != null && v.minor == null)
                    return 1;
                if (this.minor == null)
                    minor_this = 0;
                else
                    minor_this = this.minor;
                if (v.minor == null)
                    minor_v = 0;
                else
                    minor_v = v.minor;
                if (minor_this > minor_v)
                    return 1;
                else if (minor_this < minor_v)
                    return -1;
                else {
                    int revision_this, revision_v;
                    if (this.revision == null && v.revision != null)
                        return -1;
                    else if (this.revision != null && v.revision == null)
                        return 1;
                    if (this.revision == null)
                        revision_this = 0;
                    else
                        revision_this = this.revision;
                    if (v.revision == null)
                        revision_v = 0;
                    else
                        revision_v = v.revision;
                    if (revision_this > revision_v)
                        return 1;
                    else if (revision_this < revision_v)
                        return -1;
                }
            }
            return 0;
        }
    }

    public static String[] solution(String[] l) {
        Version[] v = new Version[l.length];
        Version version;
        int n1, n2;
        String major, minor, rev;
        for (int i = 0; i < l.length; i++) {
            major = null;
            minor = null;
            rev = null;
            version = new Version();
            n1 = l[i].indexOf(".");
            if (n1 != -1)
                major = l[i].substring(0, n1);
            else
                major = l[i];
            if (n1 != -1) {
                n2 = l[i].lastIndexOf(".");
                if (n2 != -1 && n2 != n1) {
                    minor = l[i].substring(n1 + 1, n2);
                    rev = l[i].substring(n2 + 1);
                } else
                    minor = l[i].substring(n1 + 1);
            }
            version.major = Integer.parseInt(major);
            if (minor != null)
                version.minor = Integer.parseInt(minor);
            if (rev != null)
                version.revision = Integer.parseInt(rev);
            v[i] = version;
        }
        Arrays.sort(v);
        for (int i = 0; i < l.length; i++) {
            String aux_string = null;
            if (v[i].major != null)
                aux_string = v[i].major.toString();
            if (v[i].minor != null)
                aux_string += "." + v[i].minor.toString();
            if (v[i].revision != null)
                aux_string += "." + v[i].revision.toString();
            l[i] =  aux_string;
        }
        return l;
    }

    public static void main(String[] args) {
        String[] strings = new String[] {"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"};
        String[] str = solution(strings);
        for (int j = 0; j < str.length; j++) {
            System.out.println(str[j]);
        }
    }
}
