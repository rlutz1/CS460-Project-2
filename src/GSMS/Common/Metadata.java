package GSMS.Common;

/**
 * this is primarily used for the data manager
 * to receive whatever information it will need
 * given the add/modify/read it is being requested to do.
 *
 * lightweight record is likely sufficient here.
 */
public record Metadata(String accompanyingInfo)
{

} // end class

