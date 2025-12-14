/**
 * Subgroup generation and analysis tools.
 * <p>
 * This package provides utilities for:
 * </p>
 * <ul>
 * <li>Generating subgroups from generator sets</li>
 * <li>Enumerating all subgroups of a group</li>
 * <li>Finding cyclic subgroups</li>
 * <li>Testing normality</li>
 * <li>Computing normalizers and centralizers</li>
 * </ul>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z12 = GroupFactory.cyclic(12);
 * CyclicElement gen = new CyclicElement(3, 12);
 * Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(gen));
 * System.out.println(h.order()); // 4
 * System.out.println(h.index()); // 3 (Lagrange's theorem: 12/4)
 * 
 * List<Subgroup<CyclicElement>> allSubs = SubgroupGenerator.allSubgroups(z12);
 * boolean isNormal = SubgroupAnalyzer.isNormal(z12, h);
 * }</pre>
 * 
 * @see d021248.group.subgroup.SubgroupGenerator
 * @see d021248.group.subgroup.SubgroupAnalyzer
 * @see d021248.group.subgroup.SpecialSubgroups
 * @see d021248.group.subgroup.Subgroup
 */
package d021248.group.subgroup;
