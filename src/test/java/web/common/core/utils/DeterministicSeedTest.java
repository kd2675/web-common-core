package web.common.core.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeterministicSeedTest {

    @Test
    void fromUtf8_sameValue_matchesMysqlSha256BackfillSeed() {
        long seed = DeterministicSeed.fromUtf8("stock-auto-1001");

        assertThat(seed)
                .isEqualTo(1_071_986_408_069_311_013L)
                .isEqualTo(DeterministicSeed.fromUtf8("stock-auto-1001"));
    }

    @Test
    void fromUtf8_differentValues_returnsDifferentSeeds() {
        long first = DeterministicSeed.fromUtf8("stock-auto-1001");
        long second = DeterministicSeed.fromUtf8("stock-auto-1002");

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void fromUtf8_null_returnsZeroForLegacyFallback() {
        assertThat(DeterministicSeed.fromUtf8(null)).isZero();
    }
}
