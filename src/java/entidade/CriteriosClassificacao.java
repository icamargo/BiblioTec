package entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Igor
 */
@Entity
@Table (name = "CriteriosClassificacao")
public class CriteriosClassificacao implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idCriterio;
    private String tipoCriterio;
    private String descCriterio;
    private int codCriterio;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CriteriosClassificacao other = (CriteriosClassificacao) obj;
        if (this.idCriterio != other.idCriterio) {
            return false;
        }
        if (this.codCriterio != other.codCriterio) {
            return false;
        }
        if (!Objects.equals(this.tipoCriterio, other.tipoCriterio)) {
            return false;
        }
        if (!Objects.equals(this.descCriterio, other.descCriterio)) {
            return false;
        }
        return true;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getTipoCriterio() {
        return tipoCriterio;
    }

    public void setTipoCriterio(String tipoCriterio) {
        this.tipoCriterio = tipoCriterio;
    }

    public String getDescCriterio() {
        return descCriterio;
    }

    public void setDescCriterio(String descCriterio) {
        this.descCriterio = descCriterio;
    }

    public int getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(int codCriterio) {
        this.codCriterio = codCriterio;
    }
}
